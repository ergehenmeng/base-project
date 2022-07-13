package com.eghm.service.business;

import com.eghm.model.dto.business.coupon.config.CouponConfigAddRequest;
import com.eghm.model.dto.business.coupon.config.CouponConfigEditRequest;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface CouponConfigService {

    /**
     * 创建优惠券配置信息
     * @param request 优惠券配置信息
     */
    void create(CouponConfigAddRequest request);

    /**
     * 更新优惠券配置信息
     * @param request 优惠券配置
     */
    void update(CouponConfigEditRequest request);
}
