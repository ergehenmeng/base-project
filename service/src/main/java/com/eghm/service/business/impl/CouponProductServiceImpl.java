package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dao.mapper.CouponProductMapper;
import com.eghm.dao.model.CouponProduct;
import com.eghm.model.dto.business.coupon.product.CouponProductRequest;
import com.eghm.service.business.CouponProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("couponProductService")
@AllArgsConstructor
public class CouponProductServiceImpl implements CouponProductService {

    private final CouponProductMapper couponProductMapper;

    @Override
    public void insert(Long couponConfigId, List<CouponProductRequest> productList) {
        if (CollUtil.isEmpty(productList)) {
            return;
        }
        productList.forEach(request -> request.getProductIds().forEach(productId -> couponProductMapper.insert(new CouponProduct(couponConfigId, request.getProductType(), productId))));
    }

    @Override
    public void insertWithDelete(Long couponConfigId, List<CouponProductRequest> productList) {
        LambdaUpdateWrapper<CouponProduct> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponProduct::getCouponConfigId, couponConfigId);
        couponProductMapper.delete(wrapper);
        this.insert(couponConfigId, productList);
    }
}
