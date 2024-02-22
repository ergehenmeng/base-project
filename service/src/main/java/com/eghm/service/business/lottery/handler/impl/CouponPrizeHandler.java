package com.eghm.service.business.lottery.handler.impl;

import com.eghm.dto.business.coupon.member.GrantCouponDTO;
import com.eghm.enums.ref.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.MemberCouponService;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wyb
 * @since 2023/4/7
 */
@Service
@AllArgsConstructor
@Slf4j
public class CouponPrizeHandler implements PrizeHandler {

    private final MemberCouponService memberCouponService;

    private final LotteryPrizeService lotteryPrizeService;

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.COUPON;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("抽中优惠券啦, [{}] [{}]", memberId, lottery);
        lotteryPrizeService.accumulationLotteryNum(config.getPrizeId());

        GrantCouponDTO dto = new GrantCouponDTO();
        dto.setNum(1);
        dto.setCouponId(config.getPrizeId());
        dto.setMemberIdList(Lists.newArrayList(memberId));
        memberCouponService.grantCoupon(dto);
    }
}
