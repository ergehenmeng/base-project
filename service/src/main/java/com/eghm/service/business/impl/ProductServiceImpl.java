package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.ProductMapper;
import com.eghm.dao.model.Product;
import com.eghm.model.dto.business.product.ProductAddRequest;
import com.eghm.model.dto.business.product.ProductEditRequest;
import com.eghm.model.dto.business.product.ProductQueryRequest;
import com.eghm.service.business.ProductService;
import com.eghm.service.business.ProductSkuService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.common.enums.ErrorCode.PRODUCT_DOWN;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productService")
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductSkuService productSkuService;

    @Override
    public Page<Product> getByPage(ProductQueryRequest request) {
        LambdaQueryWrapper<Product> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Product::getTitle, request.getQueryName());
        wrapper.eq(request.getDeliveryMethod() != null, Product::getDeliveryMethod, request.getDeliveryMethod());
        wrapper.eq(request.getState() != null, Product::getState, request.getState());
        wrapper.eq(request.getPlatformState() != null, Product::getPlatformState, request.getPlatformState());
        return productMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(ProductAddRequest request) {
        // TODO 商户id
        Product product = DataUtil.copy(request, Product.class);
        productMapper.insert(product);
        productSkuService.create(product.getId(), request.getSkuList());
    }

    @Override
    public void update(ProductEditRequest request) {
        // TODO 商户id
        Product product = DataUtil.copy(request, Product.class);
        productMapper.updateById(product);
        productSkuService.update(product.getId(), request.getSkuList());
    }

    @Override
    public Product selectById(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public Product selectByIdRequired(Long id) {
        Product product = productMapper.selectById(id);
        if (product == null) {
            log.error("该普通商品已删除 [{}]", id);
            throw new BusinessException(PRODUCT_DOWN);
        }
        return product;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getState, state);
        productMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Product> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Product::getId, id);
        wrapper.set(Product::getPlatformState, state);
        productMapper.update(null, wrapper);
    }
}
