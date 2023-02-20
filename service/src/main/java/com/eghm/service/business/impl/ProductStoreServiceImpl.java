package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.RoleType;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.ProductStoreMapper;
import com.eghm.model.Merchant;
import com.eghm.model.ProductStore;
import com.eghm.model.dto.business.product.store.ProductStoreAddRequest;
import com.eghm.model.dto.business.product.store.ProductStoreEditRequest;
import com.eghm.model.dto.business.product.store.ProductStoreQueryRequest;
import com.eghm.model.vo.business.product.store.ProductStoreHomeVO;
import com.eghm.model.vo.business.product.store.ProductStoreVO;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.ProductService;
import com.eghm.service.business.ProductStoreService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
@Service("productStoreService")
@AllArgsConstructor
@Slf4j
public class ProductStoreServiceImpl implements ProductStoreService, MerchantInitService {

    private final ProductStoreMapper productStoreMapper;

    private final ProductService productService;

    private final SysConfigApi sysConfigApi;

    @Override
    public Page<ProductStore> getByPage(ProductStoreQueryRequest request) {
        LambdaQueryWrapper<ProductStore> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), ProductStore::getTitle, request.getQueryName());
        wrapper.eq(request.getState() != null, ProductStore::getState , request.getState());
        wrapper.eq(request.getPlatformState() != null, ProductStore::getPlatformState, request.getPlatformState());
        return productStoreMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(ProductStoreAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        ProductStore shop = DataUtil.copy(request, ProductStore.class);
        shop.setMerchantId(SecurityHolder.getMerchantId());
        productStoreMapper.insert(shop);
    }

    @Override
    public void update(ProductStoreEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        ProductStore productStore = productStoreMapper.selectById(request.getId());
        ProductStore shop = DataUtil.copy(request, ProductStore.class);
        // 商户在进行注册时默认会初始化一条零售店铺(未激活状态), 更新时自动变更为激活后的状态,即:待上架
        if (productStore.getState() == State.INIT) {
            shop.setState(State.UN_SHELVE);
        }
        productStoreMapper.updateById(shop);
    }

    @Override
    public ProductStore selectById(Long id) {
        return productStoreMapper.selectById(id);
    }

    @Override
    public ProductStore selectByIdShelve(Long id) {
        ProductStore shop = productStoreMapper.selectById(id);
        if (shop == null) {
            log.error("零售店铺未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_DOWN);
        }

        if (shop.getPlatformState() != PlatformState.SHELVE) {
            log.error("零售店铺已下架 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_DOWN);
        }
        return shop;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ProductStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductStore::getId, id);
        wrapper.set(ProductStore::getState, state);
        productStoreMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<ProductStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductStore::getId, id);
        wrapper.set(ProductStore::getPlatformState, state);
        productStoreMapper.update(null, wrapper);
    }

    @Override
    public ProductStoreHomeVO homeDetail(Long id) {
        ProductStore shop = this.selectByIdShelve(id);
        ProductStoreHomeVO vo = DataUtil.copy(shop, ProductStoreHomeVO.class);
        vo.setProductList(productService.getPriorityProduct(id));
        return vo;
    }

    @Override
    public void setRecommend(Long id) {
        LambdaUpdateWrapper<ProductStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ProductStore::getId, id);
        wrapper.set(ProductStore::getRecommend, true);
        productStoreMapper.update(null, wrapper);
    }

    @Override
    public List<ProductStoreVO> getRecommend() {
        int limit = sysConfigApi.getInt(ConfigConstant.STORE_MAX_RECOMMEND, 6);
        return productStoreMapper.getRecommend(limit);
    }

    /**
     * 校验店铺名称是否重复
     * @param title 店铺名称
     * @param id id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<ProductStore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductStore::getTitle, title);
        wrapper.ne(id != null, ProductStore::getId, id);
        Integer count = productStoreMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("店铺名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.SHOP_TITLE_REDO);
        }
    }
    
    @Override
    public void init(Merchant merchant) {
        ProductStore shop = new ProductStore();
        shop.setMerchantId(merchant.getId());
        shop.setState(State.INIT);
        productStoreMapper.insert(shop);
    }
    
    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.SPECIALTY);
    }
}
