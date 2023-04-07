package com.eghm.service.business.lottery.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.LotteryLogMapper;
import com.eghm.model.LotteryLog;
import com.eghm.service.business.lottery.LotteryLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/4/3
 */
@Service("lotteryLogService")
@Slf4j
@AllArgsConstructor
public class LotteryLogServiceImpl implements LotteryLogService {

    private final LotteryLogMapper lotteryLogMapper;

    @Override
    public void insert(LotteryLog lotteryLog) {
        lotteryLogMapper.insert(lotteryLog);
    }

    @Override
    public long countLottery(Long lotteryId, Long memberId) {
        LambdaQueryWrapper<LotteryLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LotteryLog::getMemberId, memberId);
        wrapper.eq(LotteryLog::getLotteryId, memberId);
        Long aLong = lotteryLogMapper.selectCount(wrapper);
        return aLong == null ? 0L : aLong;
    }

    @Override
    public long countLottery(Long lotteryId, Long memberId, LocalDateTime startTime, LocalDateTime endTime) {
        LambdaQueryWrapper<LotteryLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LotteryLog::getMemberId, memberId);
        wrapper.eq(LotteryLog::getLotteryId, memberId);
        wrapper.between(LotteryLog::getCreateTime, startTime, endTime);
        Long aLong = lotteryLogMapper.selectCount(wrapper);
        return aLong == null ? 0L : aLong;
    }

    @Override
    public long countLotteryWin(Long lotteryId, Long memberId) {
        LambdaQueryWrapper<LotteryLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LotteryLog::getMemberId, memberId);
        wrapper.eq(LotteryLog::getLotteryId, memberId);
        wrapper.eq(LotteryLog::getWinning, true);
        Long aLong = lotteryLogMapper.selectCount(wrapper);
        return aLong == null ? 0L : aLong;
    }
}
