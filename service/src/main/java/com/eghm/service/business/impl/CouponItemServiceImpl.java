package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.CouponItemMapper;
import com.eghm.model.CouponItem;
import com.eghm.model.dto.business.coupon.product.CouponItemRequest;
import com.eghm.service.business.CouponItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Service("couponProductService")
@AllArgsConstructor
public class CouponItemServiceImpl implements CouponItemService {

    private final CouponItemMapper couponItemMapper;

    @Override
    public void insert(Long couponConfigId, List<CouponItemRequest> productList) {
        if (CollUtil.isEmpty(productList)) {
            return;
        }
        productList.forEach(request -> request.getProductIds().forEach(productId -> couponItemMapper
                .insert(new CouponItem(couponConfigId, request.getProductType(), productId))));
    }

    @Override
    public void insertWithDelete(Long couponConfigId, List<CouponItemRequest> productList) {
        LambdaUpdateWrapper<CouponItem> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponItem::getCouponConfigId, couponConfigId);
        couponItemMapper.delete(wrapper);
        this.insert(couponConfigId, productList);
    }

    @Override
    public boolean match(Long couponConfigId, Long productId) {
        LambdaQueryWrapper<CouponItem> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CouponItem::getProductId, productId);
        wrapper.eq(CouponItem::getCouponConfigId, couponConfigId);
        Integer count = couponItemMapper.selectCount(wrapper);
        return count > 0;
    }
}
