package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ref.ProductType;
import com.eghm.mapper.CouponScopeMapper;
import com.eghm.model.CouponScope;
import com.eghm.service.business.CouponScopeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Service("couponScopeService")
@AllArgsConstructor
public class CouponScopeServiceImpl implements CouponScopeService {

    private final CouponScopeMapper couponScopeMapper;

    @Override
    public void insertOnUpdate(Long couponId, List<Long> productIds, ProductType productType) {
        LambdaUpdateWrapper<CouponScope> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponScope::getCouponId, couponId);
        couponScopeMapper.delete(wrapper);
        productIds.forEach(productId -> couponScopeMapper.insert(new CouponScope(couponId, productType, productId)));
    }

    @Override
    public boolean match(Long couponId, List<Long> productIds) {
        Set<Long> ids = new HashSet<>(productIds);
        LambdaQueryWrapper<CouponScope> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CouponScope::getProductId, ids);
        wrapper.eq(CouponScope::getCouponId, couponId);
        Long count = couponScopeMapper.selectCount(wrapper);
        return count == ids.size();
    }

    @Override
    public List<Long> getProductIds(Long couponId) {
        LambdaQueryWrapper<CouponScope> wrapper = Wrappers.lambdaQuery();
        wrapper.select(CouponScope::getProductId);
        wrapper.eq(CouponScope::getCouponId, couponId);
        List<CouponScope> scopeList = couponScopeMapper.selectList(wrapper);
        return scopeList.stream().map(CouponScope::getProductId).toList();
    }
}
