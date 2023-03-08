package com.eghm.service.business;

import com.eghm.model.dto.business.coupon.product.CouponItemRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
public interface CouponItemService {

    /**
     * 批量添加优惠券与产品关联关系
     * @param couponConfigId 优惠券配置id
     * @param productList 产品列表
     */
    void insert(Long couponConfigId, List<CouponItemRequest> productList);

    /**
     * 批量添加优惠券与产品关联关系 (如果存在之前的则全部删除)
     * @param couponConfigId 优惠券配置id
     * @param productList 产品id
     */
    void insertWithDelete(Long couponConfigId, List<CouponItemRequest> productList);

    /**
     * 判断优惠券是否匹配该商品
     * @param couponConfigId 优惠券配置id
     * @param productId 商品id
     * @return true:匹配 false:不匹配
     */
    boolean match(Long couponConfigId, Long productId);
}
