package com.eghm.service.business;

import com.eghm.dto.ext.ProductScope;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
public interface CouponScopeService {

    /**
     * 批量添加优惠券与产品关联关系
     *
     * @param couponId 优惠券id
     * @param productIds 商品ids
     */
    void insertOnUpdate(Long couponId, List<ProductScope> productIds);

    /**
     * 判断优惠券是否匹配该商品
     *
     * @param couponId  优惠券id
     * @param productId 商品id
     * @return true:匹配 false:不匹配
     */
    boolean match(Long couponId, Long productId);
}
