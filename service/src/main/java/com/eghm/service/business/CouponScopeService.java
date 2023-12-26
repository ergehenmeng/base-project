package com.eghm.service.business;

import com.eghm.dto.business.coupon.product.CouponScopeRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface CouponScopeService {

    /**
     * 批量添加优惠券与产品关联关系
     * @param couponId 优惠券id
     * @param itemIds 产品列表
     */
    void insert(Long couponId, List<Long> itemIds);

    /**
     * 批量添加优惠券与产品关联关系 (如果存在之前的则全部删除)
     * @param couponId 优惠券id
     * @param itemIds 产品id
     */
    void insertWithDelete(Long couponId, List<Long> itemIds);

    /**
     * 判断优惠券是否匹配该商品
     * @param couponId 优惠券id
     * @param productId 商品id
     * @return true:匹配 false:不匹配
     */
    boolean match(Long couponId, Long productId);
}
