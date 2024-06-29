package com.eghm.service.business;

import com.eghm.enums.ref.ProductType;

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
     * @param productType 商品类型
     */
    void insertOnUpdate(Long couponId, List<Long> productIds, ProductType productType);

    /**
     * 判断优惠券是否匹配该商品
     *
     * @param couponId  优惠券id
     * @param productId 商品id
     * @return true:匹配 false:不匹配
     */
    boolean match(Long couponId, Long productId);

    /**
     * 根据优惠券id获取关联的商品id
     * @param couponId 优惠券id
     * @return 商品列表
     */
    List<Long> getProductIds(Long couponId);
}
