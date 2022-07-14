package com.eghm.service.business;

import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface UserCouponService {

    /**
     * 普通用户领取优惠券
     * @param dto 优惠券新
     */
    void receiveCoupon(ReceiveCouponDTO dto);

    /**
     * 统计某个优惠券用户领了多少个
     * @param couponId 优惠券id
     * @param userId 用户id
     * @return 个数
     */
    int receiveCount(Long couponId, Long userId);
}
