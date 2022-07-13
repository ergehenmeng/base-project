package com.eghm.service.business;

import com.eghm.model.dto.business.coupon.user.ReceiveCouponDTO;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface UserCouponService {

    void receiveCoupon(ReceiveCouponDTO dto);
}
