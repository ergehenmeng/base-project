package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.mapper.ProductShopMapper;
import com.eghm.model.ProductShop;
import com.eghm.model.dto.business.product.shop.ProductShopAddRequest;
import com.eghm.model.dto.business.product.shop.ProductShopEditRequest;
import com.eghm.model.dto.business.product.shop.ProductShopQueryRequest;
import com.eghm.service.business.ProductShopService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productShopService")
@AllArgsConstructor
@Slf4j
public class ProductShopServiceImpl implements ProductShopService {

    private final ProductShopMapper productShopMapper;

    @Override
    public Page<ProductShop> getByPage(ProductShopQueryRequest request) {
        LambdaQueryWrapper<ProductShop> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), ProductShop::getTitle, request.getQueryName());
        wrapper.eq(request.getState() != null, ProductShop::getState , request.getState());
        wrapper.eq(request.getPlatformState() != null, ProductShop::getPlatformState, request.getPlatformState());
        return productShopMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(ProductShopAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        ProductShop shop = DataUtil.copy(request, ProductShop.class);
        shop.setMerchantId(SecurityHolder.getMerchantId());
        productShopMapper.insert(shop);
    }

    @Override
    public void update(ProductShopEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        ProductShop shop = DataUtil.copy(request, ProductShop.class);
        productShopMapper.updateById(shop);
    }

    @Override
    public ProductShop selectById(Long id) {
        return productShopMapper.selectById(id);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ProductShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductShop::getId, id);
        wrapper.set(ProductShop::getState, state);
        productShopMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<ProductShop> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductShop::getId, id);
        wrapper.set(ProductShop::getPlatformState, state);
        productShopMapper.update(null, wrapper);
    }

    /**
     * 校验店铺名称是否重复
     * @param title 店铺名称
     * @param id id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<ProductShop> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductShop::getTitle, title);
        wrapper.ne(id != null, ProductShop::getId, id);
        Integer count = productShopMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("店铺名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.SHOP_TITLE_REDO);
        }
    }
}
