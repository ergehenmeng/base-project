package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.item.store.ItemStoreAddRequest;
import com.eghm.dto.business.item.store.ItemStoreEditRequest;
import com.eghm.dto.business.item.store.ItemStoreQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.CollectType;
import com.eghm.enums.ref.RoleType;
import com.eghm.enums.ref.State;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemStoreMapper;
import com.eghm.model.ItemStore;
import com.eghm.model.Merchant;
import com.eghm.service.business.*;
import com.eghm.service.business.lottery.LotteryService;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.item.store.BaseItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreHomeVO;
import com.eghm.vo.business.item.store.ItemStoreResponse;
import com.eghm.vo.business.item.store.ItemStoreVO;
import com.eghm.vo.business.lottery.LotteryVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.constants.CacheConstant.MEMBER_COLLECT;
import static com.eghm.enums.ErrorCode.SHOP_NOT_PERFECT;

/**
 * @author 二哥很猛
 * @since 2022/7/1
 */
@Service("itemStoreService")
@AllArgsConstructor
@Slf4j
public class ItemStoreServiceImpl implements ItemStoreService, MerchantInitService {

    private final ItemService itemService;

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final CommonService commonService;

    private final LotteryService lotteryService;

    private final ItemStoreMapper itemStoreMapper;

    private final MemberCollectService memberCollectService;

    @Override
    public Page<ItemStoreResponse> getByPage(ItemStoreQueryRequest request) {
        return itemStoreMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<ItemStoreResponse> getList(ItemStoreQueryRequest request) {
        return itemStoreMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public void create(ItemStoreAddRequest request) {
        this.redoTitle(request.getTitle(), null);
        ItemStore shop = DataUtil.copy(request, ItemStore.class);
        shop.setState(State.UN_SHELVE);
        shop.setCoverUrl(CollUtil.join(request.getCoverList(), ","));
        itemStoreMapper.insert(shop);
    }

    @Override
    public void update(ItemStoreEditRequest request) {
        this.redoTitle(request.getTitle(), request.getId());
        ItemStore itemStore = itemStoreMapper.selectById(request.getId());
        commonService.checkIllegal(itemStore.getMerchantId());
        ItemStore shop = DataUtil.copy(request, ItemStore.class);
        shop.setCoverUrl(CollUtil.join(request.getCoverList(), ","));
        itemStoreMapper.updateById(shop);
    }

    @Override
    public ItemStore selectByIdRequired(Long id) {
        ItemStore shop = itemStoreMapper.selectById(id);
        if (shop == null) {
            log.error("零售店铺未查询到 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_NULL);
        }
        return shop;
    }

    @Override
    public ItemStore selectByIdShelve(Long id) {
        ItemStore store = itemStoreMapper.selectById(id);
        if (store == null || store.getState() != State.SHELVE) {
            log.error("零售店铺已下架 [{}]", id);
            throw new BusinessException(ErrorCode.SHOP_DOWN);
        }
        return store;
    }

    @Override
    public Map<Long, ItemStore> selectByIdShelveMap(List<Long> ids) {
        LambdaQueryWrapper<ItemStore> wrapper = Wrappers.lambdaQuery();
        wrapper.in(ItemStore::getId, ids);
        wrapper.eq(ItemStore::getState, State.SHELVE);
        List<ItemStore> storeList = itemStoreMapper.selectBatchIds(ids);
        if (storeList.size() != ids.size()) {
            log.error("存在已删除零售店铺 {}", ids);
            throw new BusinessException(ErrorCode.ANY_SHOP_DOWN);
        }
        return storeList.stream().collect(Collectors.toMap(ItemStore::getId, Function.identity(), (itemStore, itemStore2) -> itemStore));
    }

    @Override
    public void updateState(Long id, State state) {
        if (state == State.SHELVE) {
            ItemStore selected = this.selectByIdRequired(id);
            BeanValidator.validate(selected, SHOP_NOT_PERFECT);
        }
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, ItemStore::getMerchantId, merchantId);
        wrapper.set(ItemStore::getState, state);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public ItemStoreHomeVO homeDetail(Long id) {
        ItemStore shop = this.selectByIdShelve(id);
        ItemStoreHomeVO vo = DataUtil.copy(shop, ItemStoreHomeVO.class);
        vo.setItemList(itemService.getPriorityItem(id));
        vo.setCollect(memberCollectService.checkCollect(id, CollectType.ITEM_STORE));
        String key = String.format(MEMBER_COLLECT, CollectType.ITEM_STORE.getValue(), id);
        vo.setCommentNum(cacheService.getHashSize(key));
        List<LotteryVO> voList = lotteryService.getList(shop.getMerchantId(), id);
        vo.setLotteryList(voList);
        return vo;
    }

    @Override
    public List<ItemStoreVO> getRecommend() {
        int limit = sysConfigApi.getInt(ConfigConstant.STORE_MAX_RECOMMEND, 6);
        return itemStoreMapper.getRecommend(limit);
    }

    @Override
    public void setRecommend(Long id, boolean recommend) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        wrapper.set(ItemStore::getRecommend, recommend);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemStore::getId, id);
        Long merchantId = SecurityHolder.getMerchantId();
        wrapper.eq(merchantId != null, ItemStore::getMerchantId, merchantId);
        wrapper.set(ItemStore::getState, State.UN_SHELVE);
        wrapper.set(ItemStore::getDeleted, true);
        itemStoreMapper.update(null, wrapper);
    }

    @Override
    public Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request) {
        return itemStoreMapper.getStorePage(Boolean.TRUE.equals(request.getLimit()) ? request.createPage() : request.createNullPage(), request);
    }

    @Override
    public List<BaseItemStoreResponse> getList(Long merchantId) {
        return itemStoreMapper.getBaseList(merchantId);
    }

    @Override
    public void logout(Long merchantId) {
        LambdaUpdateWrapper<ItemStore> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(ItemStore::getState, State.FORCE_UN_SHELVE);
        wrapper.eq(ItemStore::getMerchantId, merchantId);
        itemStoreMapper.update(null, wrapper);
        itemService.logout(merchantId);
    }

    /**
     * 校验店铺名称是否重复
     *
     * @param title 店铺名称
     * @param id    id 编辑时不能为空
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
        shop.setTitle(merchant.getMerchantName());
        shop.setState(State.UN_SHELVE);
        shop.setMerchantId(merchant.getId());
        itemStoreMapper.insert(shop);
    }

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.ITEM);
    }
}
