package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ref.AuditState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.ProductMapper;
import com.eghm.dao.model.Product;
import com.eghm.model.dto.business.product.ProductAddRequest;
import com.eghm.model.dto.business.product.ProductEditRequest;
import com.eghm.service.business.ProductService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productService")
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    @Override
    public void create(ProductAddRequest request) {
        // TODO 商户id
        Product product = DataUtil.copy(request, Product.class);
        productMapper.insert(product);
        // TODO SKU新增
    }

    @Override
    public void update(ProductEditRequest request) {
        Product product = DataUtil.copy(request, Product.class);
        productMapper.updateById(product);
        // TODO SKU更新
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getState, state);
        productMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, AuditState state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getAuditState, state);
        productMapper.update(null, wrapper);
    }
}
