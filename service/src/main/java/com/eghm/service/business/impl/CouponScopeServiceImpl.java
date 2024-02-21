package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.ProductScope;
import com.eghm.mapper.CouponScopeMapper;
import com.eghm.model.CouponScope;
import com.eghm.service.business.CouponScopeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/13
 */
@Service("couponScopeService")
@AllArgsConstructor
public class CouponScopeServiceImpl implements CouponScopeService {

    private final CouponScopeMapper couponScopeMapper;

    @Override
    public void insertOnUpdate(Long couponId, List<ProductScope> productIds) {
        LambdaUpdateWrapper<CouponScope> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponScope::getCouponId, couponId);
        couponScopeMapper.delete(wrapper);
        productIds.forEach(scope -> couponScopeMapper.insert(new CouponScope(couponId, scope)));
    }

    @Override
    public boolean match(Long couponId, Long productId) {
        LambdaQueryWrapper<CouponScope> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(CouponScope::getProductId, productId);
        wrapper.eq(CouponScope::getCouponId, couponId);
        Long count = couponScopeMapper.selectCount(wrapper);
        return count > 0;
    }
}
