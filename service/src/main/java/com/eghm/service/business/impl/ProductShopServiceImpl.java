package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.ProductShopMapper;
import com.eghm.dao.model.ProductShop;
import com.eghm.model.dto.business.product.shop.ProductShopAddRequest;
import com.eghm.model.dto.business.product.shop.ProductShopEditRequest;
import com.eghm.service.business.ProductShopService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productShopService")
@AllArgsConstructor
public class ProductShopServiceImpl implements ProductShopService {

    private final ProductShopMapper productShopMapper;

    @Override
    public void create(ProductShopAddRequest request) {
        // TODO 商户id增加
        ProductShop shop = DataUtil.copy(request, ProductShop.class);
        productShopMapper.insert(shop);
    }

    @Override
    public void update(ProductShopEditRequest request) {
        ProductShop shop = DataUtil.copy(request, ProductShop.class);
        productShopMapper.updateById(shop);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ProductShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductShop::getId, id);
        wrapper.set(ProductShop::getState, state);
        productShopMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<ProductShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductShop::getId, id);
        wrapper.set(ProductShop::getAuditState, state);
        productShopMapper.update(null, wrapper);
    }
}
