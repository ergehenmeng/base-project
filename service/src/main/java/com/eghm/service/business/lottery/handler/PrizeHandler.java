package com.eghm.service.business.lottery.handler;

import com.eghm.enums.ref.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;

/**
 * @author wyb
 * @since 2023/4/7
 */
public interface PrizeHandler {

    /**
     * 是否支持该奖品发放
     *
     * @param prizeType 奖品类型
     * @return true:支持 false:不支持
     */
    boolean supported(PrizeType prizeType);

    /**
     * 发放抽奖奖品
     *
     * @param memberId 用户ID
     * @param lottery  抽奖信息
     * @param config   中奖信息
     */
    void execute(Long memberId, Lottery lottery, LotteryConfig config);
}
