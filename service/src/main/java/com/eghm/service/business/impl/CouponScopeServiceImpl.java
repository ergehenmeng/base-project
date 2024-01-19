package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ref.ProductType;
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
    public void insert(Long couponId, List<Long> itemIds) {
        if (CollUtil.isEmpty(itemIds)) {
            return;
        }
        itemIds.forEach(itemId -> couponScopeMapper.insert(new CouponScope(couponId, ProductType.ITEM, itemId)));
    }

    @Override
    public void insertWithDelete(Long couponId, List<Long> itemIds) {
        LambdaUpdateWrapper<CouponScope> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(CouponScope::getCouponId, couponId);
        couponScopeMapper.delete(wrapper);
        this.insert(couponId, itemIds);
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
