package com.eghm.service.business.lottery.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.lottery.*;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.LotteryState;
import com.eghm.enums.ref.PrizeType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LotteryMapper;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.model.LotteryLog;
import com.eghm.model.LotteryPrize;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.lottery.LotteryConfigService;
import com.eghm.service.business.lottery.LotteryLogService;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.lottery.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    private final LotteryMapper lotteryMapper;

    private final LotteryPrizeService lotteryPrizeService;

    private final LotteryConfigService lotteryConfigService;

    private final LotteryLogService lotteryLogService;

    private final List<PrizeHandler> handlerList;

    private final CommonService commonService;

    @Override
    public Page<LotteryResponse> getByPage(LotteryQueryRequest request) {
        return lotteryMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void create(LotteryAddRequest request) {
        this.checkConfig(request.getConfigList());
        this.checkPrize(request.getPrizeList(), request.getConfigList());
        this.redoTitle(request.getTitle(), null, request.getMerchantId());
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lottery.setState(this.calcState(request.getStartTime(), request.getEndTime()));
        lotteryMapper.insert(lottery);
        Map<Integer, LotteryPrize> prizeMap = lotteryPrizeService.insert(lottery.getId(), request.getPrizeList());
        lotteryConfigService.insert(lottery.getId(), request.getConfigList(), prizeMap);
    }

    @Override
    public void update(LotteryEditRequest request) {
        this.checkConfig(request.getConfigList());
        this.checkPrize(request.getPrizeList(), request.getConfigList());
        this.redoTitle(request.getTitle(), request.getId(), request.getMerchantId());
        Lottery select = lotteryMapper.selectById(request.getId());
        if (select.getState() != LotteryState.INIT) {
            throw new BusinessException(ErrorCode.LOTTERY_NOT_INIT);
        }
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lottery.setState(this.calcState(request.getStartTime(), request.getEndTime()));
        lotteryMapper.updateById(lottery);
        Map<Integer, LotteryPrize> prizeMap = lotteryPrizeService.update(lottery.getId(), request.getPrizeList());
        lotteryConfigService.update(lottery.getId(), request.getConfigList(), prizeMap);
    }

    @Override
    public LotteryResultVO lottery(Long lotteryId, Long memberId) {
        Lottery lottery = this.selectById(lotteryId);
        if (lottery == null) {
            log.error("抽奖活动可能已删除 [{}]", lotteryId);
            throw new BusinessException(ErrorCode.LOTTERY_NULL);
        }
        this.checkLottery(lottery, memberId);
        List<LotteryConfig> configList = lotteryConfigService.getList(lottery.getId());
        // 随便查询一个未中奖的配置
        LotteryConfig losingLottery = this.getLosingLottery(configList);
        // 抽奖过程
        LotteryConfig config = this.doLottery(memberId, lottery, configList, losingLottery);
        // 发放奖品
        boolean status = this.givePrize(memberId, lottery, config);
        if (!status) {
            // 如果发放奖品失败, 默认还是按未中奖
            config = losingLottery;
        }
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setLotteryId(lotteryId);
        lotteryLog.setMemberId(memberId);
        lotteryLog.setLocation(config.getLocation());
        lotteryLog.setPrizeId(config.getPrizeId());
        lotteryLog.setWinning(config.getPrizeType() != PrizeType.NONE);
        lotteryLogService.insert(lotteryLog);

        LotteryResultVO vo = new LotteryResultVO();
        vo.setLocation(config.getLocation());
        vo.setWinning(lotteryLog.getWinning());
        LotteryPrize prize = lotteryPrizeService.selectById(lotteryLog.getPrizeId());
        vo.setCoverUrl(prize.getCoverUrl());
        vo.setPrizeName(prize.getPrizeName());
        return vo;
    }

    @Override
    public Lottery selectById(Long lotteryId) {
        return lotteryMapper.selectById(lotteryId);
    }

    @Override
    public Lottery selectByIdRequired(Long lotteryId) {
        Lottery lottery = lotteryMapper.selectById(lotteryId);
        if (lottery == null) {
            log.error("抽奖活动可能已删除 [{}]", lotteryId);
            throw new BusinessException(ErrorCode.LOTTERY_DELETE);
        }
        return lottery;
    }

    @Override
    public LotteryDetailResponse getDetailById(Long lotteryId) {
        Lottery lottery = this.selectByIdRequired(lotteryId);
        commonService.checkIllegal(lottery.getMerchantId());
        LotteryDetailResponse response = DataUtil.copy(lottery, LotteryDetailResponse.class);
        List<LotteryPrize> prizeList = lotteryPrizeService.getList(lotteryId);
        response.setPrizeList(DataUtil.copy(prizeList, LotteryPrizeResponse.class));
        List<LotteryConfig> configList = lotteryConfigService.getList(lottery.getId());
        response.setConfigList(DataUtil.copy(configList, LotteryConfigResponse.class));
        return response;
    }

    @Override
    public List<LotteryVO> getList(Long merchantId, Long storeId) {
        return lotteryMapper.getList(merchantId, storeId);
    }

    @Override
    public LotteryDetailVO detail(Long lotteryId, Long memberId) {
        Lottery lottery = this.selectByIdRequired(lotteryId);
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(lottery.getEndTime()) && lottery.getState() != LotteryState.END) {
            lottery.setState(LotteryState.END);
            lotteryMapper.updateById(lottery);
        }
        LocalDateTime startTime = LocalDateTimeUtil.beginOfDay(now);
        LocalDateTime endTime = LocalDateTimeUtil.endOfDay(now);
        long countLottery = lotteryLogService.countLottery(lottery.getId(), memberId, startTime, endTime);

        List<LotteryPrizeVO> prizeList = lotteryPrizeService.getPrizeList(lotteryId);
        LotteryDetailVO vo = DataUtil.copy(lottery, LotteryDetailVO.class);
        vo.setLotteryNum(countLottery);
        vo.setPrizeList(prizeList);
        return vo;
    }

    /**
     * 查询未中奖的配置信息
     *
     * @param configList 转盘各个位置的配置
     * @return 未中奖配置
     */
    private LotteryConfig getLosingLottery(List<LotteryConfig> configList) {
        return configList.stream().filter(c -> c.getPrizeType() == PrizeType.NONE).findFirst().orElseThrow(() -> new BusinessException(ErrorCode.LOTTERY_EXECUTE));
    }

    /**
     * 发放奖品
     *
     * @param memberId 用户id
     * @param lottery  抽奖信息
     * @param config   中奖信息
     */
    private boolean givePrize(Long memberId, Lottery lottery, LotteryConfig config) {
        try {
            handlerList.stream().filter(prizeHandler -> prizeHandler.supported(config.getPrizeType())).findFirst().orElseThrow(() -> {
                log.error("本次中奖奖品没有配置 [{}] [{}]", lottery.getId(), config.getPrizeType());
                return new BusinessException(ErrorCode.LOTTERY_PRIZE_ERROR);
            }).execute(memberId, lottery, config);
            return true;
        } catch (Exception e) {
            log.error("发放奖品异常 [{}] [{}] ", memberId, config, e);
        }
        return false;
    }

    /**
     * 抽奖
     *
     * @param memberId   用户ID
     * @param lottery    活动信息
     * @param configList 配置信息
     * @return 中奖位置
     */
    private LotteryConfig doLottery(Long memberId, Lottery lottery, List<LotteryConfig> configList, LotteryConfig losingLottery) {
        long lotteryWin = lotteryLogService.countLotteryWin(lottery.getId(), memberId);
        // 已经超过中奖次数,默认不再中奖
        if (lottery.getWinNum() <= lotteryWin) {
            return losingLottery;
        }
        int index = ThreadLocalRandom.current().nextInt(10000);
        return configList.stream().filter(config -> config.getStartRange() >= index && index < config.getEndRange()).findFirst().orElse(losingLottery);
    }

    /**
     * 校验是否可以抽奖
     *
     * @param lottery  抽奖信息
     * @param memberId 用户id
     */
    private void checkLottery(Lottery lottery, Long memberId) {
        if (lottery.getState() != LotteryState.START) {
            log.error("抽奖活动不在进行中 [{}] [{}]", lottery.getId(), lottery.getState());
            throw new BusinessException(ErrorCode.LOTTERY_STATE);
        }
        LocalDateTime now = LocalDateTime.now();
        if (lottery.getStartTime().isAfter(now) || lottery.getEndTime().isBefore(now)) {
            log.error("抽奖活动不在有效期 [{}] [{}] [{}]", lottery.getId(), lottery.getStartTime(), lottery.getEndTime());
            throw new BusinessException(ErrorCode.LOTTERY_EXPIRE);
        }
        long countLottery = lotteryLogService.countLottery(lottery.getId(), memberId);
        if (lottery.getLotteryTotal() <= countLottery) {
            log.error("用户抽奖次数用完了 [{}] 最大次数:[{}] 已抽次数:[{}]", lottery.getId(), lottery.getLotteryTotal(), countLottery);
            throw new BusinessException(ErrorCode.LOTTERY_TIME_EMPTY);
        }
        LocalDateTime startTime = LocalDateTimeUtil.beginOfDay(now);
        LocalDateTime endTime = LocalDateTimeUtil.endOfDay(now);
        countLottery = lotteryLogService.countLottery(lottery.getId(), memberId, startTime, endTime);
        if (lottery.getLotteryDay() <= countLottery) {
            log.error("用户今日抽奖次数用完了 [{}] 最大次数:[{}] 已抽次数:[{}]", lottery.getId(), lottery.getLotteryDay(), countLottery);
            throw new BusinessException(ErrorCode.LOTTERY_TIME_EMPTY);
        }
    }

    /**
     * 由于中奖的商品可能会存在库存为0的情况, 因此当库存为0时,默认不中奖, 为了防止抽奖时谢谢参与类的奖品, 此处必须包含最少一个谢谢参与的中奖位置(概率可以为0)
     *
     * @param prizeList 奖品信息
     * @param configList 中奖位置信息
     */
    private void checkPrize(List<LotteryPrizeRequest> prizeList, List<LotteryConfigRequest> configList) {
        AtomicInteger index = new AtomicInteger(0);
        Map<Integer, PrizeType> prizeTypeMap = prizeList.stream().collect(Collectors.toMap(lotteryPrizeRequest -> index.getAndIncrement(), LotteryPrizeRequest::getPrizeType));
        boolean anyMatch = configList.stream().anyMatch(config -> prizeTypeMap.get(config.getPrizeIndex()) == PrizeType.NONE);
        if (!anyMatch) {
            throw new BusinessException(ErrorCode.LOTTERY_PRIZE_TYPE);
        }
    }

    /**
     * 校验中奖配置信息
     *
     * @param configList 配置信息
     */
    private void checkConfig(List<LotteryConfigRequest> configList) {
        Optional<BigDecimal> optional = configList.stream().map(LotteryConfigRequest::getRatio).reduce(BigDecimal::add);
        if (!optional.isPresent() || optional.get().compareTo(BigDecimal.valueOf(100L)) != 0) {
            throw new BusinessException(ErrorCode.LOTTERY_RATIO);
        }
    }

    /**
     * 校验抽奖活动名称是否重复, 主要:同一个商户下不能重复
     *
     * @param title      活动名称
     * @param id         活动id
     * @param merchantId 商户id
     */
    private void redoTitle(String title, Long id, Long merchantId) {
        LambdaQueryWrapper<Lottery> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Lottery::getMerchantId, merchantId);
        wrapper.eq(Lottery::getTitle, title);
        wrapper.ne(id != null, Lottery::getId, id);
        Long count = lotteryMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("抽奖活动名称重复 [{}] [{}] [{}]", title, id, merchantId);
            throw new BusinessException(ErrorCode.LOTTERY_REDO);
        }
    }

    /**
     * 计算抽奖状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 状态
     */
    private LotteryState calcState(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            return LotteryState.INIT;
        }
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startTime)) {
            return LotteryState.INIT;
        }
        return now.isAfter(endTime) ? LotteryState.END : LotteryState.START;
    }
}
