package com.eghm.service.business.lottery.handler.impl;

import com.eghm.enums.ref.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/4/7
 */
@Service
public class CouponPrizeHandler implements PrizeHandler {

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.COUPON;
    }

    @Override
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {

    }
}
