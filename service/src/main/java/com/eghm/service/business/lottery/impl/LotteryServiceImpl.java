package com.eghm.service.business.lottery.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryConfigRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.LotteryState;
import com.eghm.enums.ref.PrizeType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LotteryMapper;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.model.LotteryLog;
import com.eghm.model.LotteryPrize;
import com.eghm.service.business.lottery.LotteryConfigService;
import com.eghm.service.business.lottery.LotteryLogService;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
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

    @Override
    public void create(LotteryAddRequest request) {
        this.checkConfig(request.getConfigList());
        this.redoTitle(request.getTitle(), null, SecurityHolder.getMerchantId());
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lotteryMapper.insert(lottery);
        List<LotteryPrize> prizeList = lotteryPrizeService.insert(lottery.getId(), request.getPrizeList());
        List<Long> prizeIds = prizeList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        lotteryConfigService.insert(lottery.getId(), request.getConfigList(), prizeIds);
    }

    @Override
    public void update(LotteryEditRequest request) {
        this.checkConfig(request.getConfigList());
        this.redoTitle(request.getTitle(), request.getId(), SecurityHolder.getMerchantId());
        Lottery select = lotteryMapper.selectById(request.getId());
        if (select.getState() != LotteryState.INIT) {
            throw new BusinessException(ErrorCode.LOTTERY_NOT_INIT);
        }
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lotteryMapper.updateById(lottery);
        List<LotteryPrize> prizeList = lotteryPrizeService.update(lottery.getId(), request.getPrizeList());
        List<Long> prizeIds = prizeList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        lotteryConfigService.update(lottery.getId(), request.getConfigList(), prizeIds);
    }

    @Override
    public void lottery(Long lotteryId, Long userId) {
        Lottery lottery = this.selectByIdRequired(lotteryId);
        this.checkLottery(lottery, userId);
        List<LotteryConfig> configList = lotteryConfigService.getList(lottery.getId());
        LotteryConfig config = this.doLottery(userId, lottery, configList);

        boolean status = this.givePrize(userId, lottery, config);
        if (!status) {
            config = configList.get(configList.size() - 1);
        }
        LotteryLog lotteryLog = new LotteryLog();
        lotteryLog.setLotteryId(lotteryId);
        lotteryLog.setMemberId(userId);
        lotteryLog.setLocation(config.getLocation());
        lotteryLog.setPrizeId(config.getPrizeId());
        lotteryLog.setWinning(config.getPrizeType() != PrizeType.NONE);
        lotteryLogService.insert(lotteryLog);
    }

    @Override
    public Lottery selectByIdRequired(Long lotteryId) {
        Lottery lottery = lotteryMapper.selectById(lotteryId);
        if (lottery == null) {
            log.error("抽奖活动可能已删除 [{}]", lotteryId);
            throw new BusinessException(ErrorCode.LOTTERY_NULL);
        }
        return lottery;
    }


    /**
     * 发放奖品
     * @param userId 用户id
     * @param lottery 抽奖信息
     * @param config 中奖信息
     */
    private boolean givePrize(Long userId, Lottery lottery, LotteryConfig config) {
        try {
            handlerList.stream().filter(prizeHandler -> prizeHandler.supported(config.getPrizeType())).findFirst().orElseThrow(() -> {
                log.error("本次中奖奖品没有配置 [{}] [{}]", lottery.getId(), config.getPrizeType());
                return new BusinessException(ErrorCode.LOTTERY_PRIZE_ERROR);
            }).execute(userId, lottery, config);
            return true;
        } catch (BusinessException e) {
            log.error("发放奖品异常 [{}] [{}] ", userId, config, e);
        }
        return false;
    }


    /**
     * 抽奖
     * @param userId 用户ID
     * @param lottery 活动信息
     * @param configList 配置信息
     * @return 中奖位置
     */
    private LotteryConfig doLottery(Long userId, Lottery lottery, List<LotteryConfig> configList) {
        long lotteryWin = lotteryLogService.countLotteryWin(lottery.getId(), userId);
        // 已经超过中奖次数,默认不再中奖,最后一个位置默认都是不中奖
        LotteryConfig freeConfig = configList.get(configList.size() - 1);
        if (lottery.getWinNum() <= lotteryWin) {
            return freeConfig;
        }
        int index = ThreadLocalRandom.current().nextInt(10000);
        return configList.stream().filter(config -> config.getStartRange() >= index && index < config.getEndRange()).findFirst().orElse(freeConfig);
    }

    /**
     * 校验是否可以抽奖
     * @param lottery 抽奖信息
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
        if (lottery.getLotteryTotal() <= countLottery) {
            log.error("用户今日抽奖次数用完了 [{}] 最大次数:[{}] 已抽次数:[{}]", lottery.getId(), lottery.getLotteryDay(), countLottery);
            throw new BusinessException(ErrorCode.LOTTERY_TIME_EMPTY);
        }
    }

    /**
     * 校验中奖配置信息
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
     * @param title 活动名称
     * @param id 活动id
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
    
}
