package com.eghm.service.business.lottery.handler.impl;

import com.eghm.dto.business.coupon.user.GrantCouponDTO;
import com.eghm.enums.ref.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.UserCouponService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/4/7
 */
@Service
@AllArgsConstructor
@Slf4j
public class CouponPrizeHandler implements PrizeHandler {

    private final UserCouponService userCouponService;

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.COUPON;
    }

    @Override
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("抽中优惠券啦 [{}] [{}]", memberId, lottery);
        GrantCouponDTO dto = new GrantCouponDTO();
        dto.setNum(1);
        dto.setCouponConfigId(config.getPrizeId());
        dto.setUserIdList(Lists.newArrayList(memberId));
        userCouponService.grantCoupon(dto);
    }
}
