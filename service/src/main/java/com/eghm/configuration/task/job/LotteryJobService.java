package com.eghm.configuration.task.job;

import com.eghm.configuration.annotation.CronMark;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 二哥很猛
 * @since 2024/8/26
 */

@Slf4j
@AllArgsConstructor
@Component("lotteryJobService")
public class LotteryJobService {

    private final LotteryService lotteryService;

    private final LotteryPrizeService lotteryPrizeService;

    @CronMark
    public void lotteryRelease() {
        LoggerUtil.print("抽奖过期奖品数量释放任务开始执行");
        lotteryService.getEndIds().forEach(lotteryId -> lotteryPrizeService.releaseSemaphore(lotteryId, null));
        LoggerUtil.print("抽奖过期奖品数量释放任务执行完毕");
    }
}
