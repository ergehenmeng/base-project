package com.eghm.service.business.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.dto.business.lottery.LotteryPrizeConfigRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.LotteryState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.LotteryMapper;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryPrize;
import com.eghm.service.business.LotteryLogService;
import com.eghm.service.business.LotteryPrizeConfigService;
import com.eghm.service.business.LotteryPrizeService;
import com.eghm.service.business.LotteryService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    
    private final LotteryPrizeConfigService lotteryPrizeConfigService;

    private final LotteryLogService lotteryLogService;

    @Override
    public void create(LotteryAddRequest request) {
        this.checkConfig(request.getConfigList());
        this.redoTitle(request.getTitle(), null, SecurityHolder.getMerchantId());
        Lottery lottery = DataUtil.copy(request, Lottery.class);
        lotteryMapper.insert(lottery);
        List<LotteryPrize> prizeList = lotteryPrizeService.insert(lottery.getId(), request.getPrizeList());
        List<Long> prizeIds = prizeList.stream().map(LotteryPrize::getId).collect(Collectors.toList());
        lotteryPrizeConfigService.insert(lottery.getId(), request.getConfigList(), prizeIds);
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
        lotteryPrizeConfigService.update(lottery.getId(), request.getConfigList(), prizeIds);
    }

    @Override
    public void lottery(Long lotteryId, Long userId) {
        Lottery lottery = this.selectByIdRequired(lotteryId);
        this.checkLottery(lottery, userId);

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
    private void checkConfig(List<LotteryPrizeConfigRequest> configList) {
        Optional<BigDecimal> optional = configList.stream().map(LotteryPrizeConfigRequest::getRatio).reduce(BigDecimal::add);
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
