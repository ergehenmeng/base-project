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
import com.eghm.mapper.ItemStoreMapper;
import com.eghm.model.ItemStore;
import com.eghm.model.Merchant;
import com.eghm.model.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.model.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.model.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.model.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.model.vo.business.item.store.ItemStoreVO;
import com.eghm.service.business.CommonService;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemStoreService;
import com.eghm.service.business.MerchantInitService;
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
@Service("itemStoreService")
@AllArgsConstructor
@Slf4j
public class ItemStoreServiceImpl implements ItemStoreService, MerchantInitService {

    private final ItemStoreMapper itemStoreMapper;

    private final ItemService itemService;

    private final SysConfigApi sysConfigApi;
    
    private final CommonService commonService;

    @Override
    public Page<ItemStore> getByPage(ItemStoreQueryRequest request) {
        LambdaQueryWrapper<ItemStore> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), ItemStore::getTitle, request.getQueryName());
        wrapper.eq(request.getState() != null, ItemStore::getState , request.getState());
        wrapper.eq(request.getPlatformState() != null, ItemStore::getPlatformState, request.getPlatformState());
        return itemStoreMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(ItemStoreAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        ItemStore shop = DataUtil.copy(request, ItemStore.class);
        shop.setState(State.UN_SHELVE);
        shop.setMerchantId(SecurityHolder.getMerchantId());
        itemStoreMapper.insert(shop);
    }

    @Override
    public void update(ItemStoreEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        ItemStore itemStore = itemStoreMapper.selectById(request.getId());
        commonService.checkIllegal(itemStore.getMerchantId());
        
        ItemStore shop = DataUtil.copy(request, ItemStore.class);
        // 商户在进行注册时默认会初始化一条零售店铺(未激活状态), 更新时自动变更为激活后的状态,即:待上架
        if (itemStore.getState() == State.INIT) {
            shop.setState(State.UN_SHELVE);
        }
        itemStoreMapper.updateById(shop);
    }

    @Override
    public ItemStore selectByIdRequired(Long id) {
        ItemStore shop = itemStoreMapper.selectById(id);
        if (shop == null) {
            log.error("零售店铺未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_DOWN);
        }
        return shop;
    }

    @Override
    public ItemStore selectByIdShelve(Long id) {
        ItemStore store = this.selectByIdRequired(id);
        if (store.getPlatformState() != PlatformState.SHELVE) {
            log.error("零售店铺已下架 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_DOWN);
        }
        return store;
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        wrapper.set(ItemStore::getState, state);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        wrapper.set(ItemStore::getPlatformState, state);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public ItemStoreHomeVO homeDetail(Long id) {
        ItemStore shop = this.selectByIdShelve(id);
        ItemStoreHomeVO vo = DataUtil.copy(shop, ItemStoreHomeVO.class);
        vo.setItemList(itemService.getPriorityItem(id));
        return vo;
    }

    @Override
    public void setRecommend(Long id) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        wrapper.set(ItemStore::getRecommend, true);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public List<ItemStoreVO> getRecommend() {
        int limit = sysConfigApi.getInt(ConfigConstant.STORE_MAX_RECOMMEND, 6);
        return itemStoreMapper.getRecommend(limit);
    }

    /**
     * 校验店铺名称是否重复
     * @param title 店铺名称
     * @param id id 编辑时不能为空
     */
    private void redoTitle(String title, Long id) {
        LambdaQueryWrapper<ItemStore> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemStore::getTitle, title);
        wrapper.ne(id != null, ItemStore::getId, id);
        Long count = itemStoreMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("店铺名称重复 [{}] [{}]", title, id);
            throw new BusinessException(ErrorCode.SHOP_TITLE_REDO);
        }
    }
    
    @Override
    public void init(Merchant merchant) {
        ItemStore shop = new ItemStore();
        shop.setMerchantId(merchant.getId());
        shop.setState(State.INIT);
        itemStoreMapper.insert(shop);
    }
    
    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.SPECIALTY);
    }
}
