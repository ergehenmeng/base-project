package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.common.exception.BusinessException;
import com.eghm.constants.ConfigConstant;
import com.eghm.mapper.CouponConfigMapper;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.CouponConfig;
import com.eghm.model.Item;
import com.eghm.model.ItemSpec;
import com.eghm.model.dto.business.product.ItemAddRequest;
import com.eghm.model.dto.business.product.ItemCouponQueryDTO;
import com.eghm.model.dto.business.product.ItemEditRequest;
import com.eghm.model.dto.business.product.ItemQueryDTO;
import com.eghm.model.dto.business.product.ItemQueryRequest;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;
import com.eghm.model.dto.business.product.sku.ItemSpecRequest;
import com.eghm.model.vo.business.item.ItemListResponse;
import com.eghm.model.vo.business.item.ItemListVO;
import com.eghm.model.vo.business.item.ItemResponse;
import com.eghm.model.vo.business.item.ItemSkuResponse;
import com.eghm.model.vo.business.item.ItemSpecResponse;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.ItemSpecService;
import com.eghm.service.sys.impl.SysConfigApi;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.common.enums.ErrorCode.PRODUCT_DOWN;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
@Service("itemService")
@Slf4j
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    
    private final ItemMapper itemMapper;
    
    private final ItemSkuService itemSkuService;
    
    private final ItemSpecService itemSpecService;
    
    private final SysConfigApi sysConfigApi;
    
    private final CouponConfigMapper couponConfigMapper;
    
    @Override
    public Page<ItemListResponse> getByPage(ItemQueryRequest request) {
        // TODO 过滤当前登录人的storeId
        return itemMapper.listPage(request.createPage(), request);
    }
    
    @Override
    public void create(ItemAddRequest request) {
        this.checkSpec(request.getMultiSpec(), request.getSpecList());
        this.titleRedo(request.getTitle(), null, request.getStoreId());
        
        Item item = DataUtil.copy(request, Item.class);
        this.setMinMaxPrice(item, request.getSkuList());
        // 总销量需要添加虚拟销量
        item.setTotalNum(request.getSkuList().stream().mapToInt(ItemSkuRequest::getVirtualNum).sum());
        itemMapper.insert(item);
        
        Map<String, Long> specMap = itemSpecService.insert(item, request.getSpecList());
        itemSkuService.insert(item, specMap, request.getSkuList());
    }
    
    @Override
    public void update(ItemEditRequest request) {
        this.checkSpec(request.getMultiSpec(), request.getSpecList());
        this.titleRedo(request.getTitle(), request.getId(), request.getStoreId());
        Item item = DataUtil.copy(request, Item.class);
        itemMapper.updateById(item);
        
        Map<String, Long> specMap = itemSpecService.update(item, request.getSpecList());
        itemSkuService.update(item, specMap, request.getSkuList());
    }
    
    @Override
    public ItemResponse getDetailById(Long itemId) {
        Item item = this.selectByIdRequired(itemId);
        ItemResponse response = DataUtil.copy(item, ItemResponse.class);
        // 多规格才会保存规格配置信息
        if (Boolean.TRUE.equals(item.getMultiSpec())) {
            List<ItemSpec> spec = itemSpecService.getByItemId(itemId);
            List<ItemSpecResponse> specList = DataUtil.copy(spec, ItemSpecResponse.class);
            // 根据规格名分组
            LinkedHashMap<String, List<ItemSpecResponse>> specMap = specList.stream().collect(Collectors.groupingBy(ItemSpecResponse::getSpecName, LinkedHashMap::new, Collectors.toList()));
            response.setSpecMap(specMap);
        }
        List<ProductSku> skuList = itemSkuService.getByItemId(itemId);
        response.setSkuList(DataUtil.copy(skuList, ItemSkuResponse.class));
        return response;
    }
    
    @Override
    public Item selectById(Long itemId) {
        return itemMapper.selectById(itemId);
    }
    
    @Override
    public Item selectByIdRequired(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            log.error("该零售商品已删除 [{}]", itemId);
            throw new BusinessException(PRODUCT_DOWN);
        }
        return item;
    }
    
    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getState, state);
        itemMapper.update(null, wrapper);
    }
    
    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getPlatformState, state);
        itemMapper.update(null, wrapper);
    }
    
    @Override
    public void setRecommend(Long id) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getRecommend, true);
        itemMapper.update(null, wrapper);
    }
    
    @Override
    public void sortBy(Long id, Integer sortBy) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Item::getId, id);
        wrapper.set(Item::getSortBy, sortBy);
        itemMapper.update(null, wrapper);
    }
    
    @Override
    public Map<Long, Item> getByIds(Set<Long> ids) {
        LambdaUpdateWrapper<Item> wrapper = Wrappers.lambdaUpdate();
        wrapper.in(Item::getId, ids);
        wrapper.eq(Item::getPlatformState, PlatformState.SHELVE);
        List<Item> productList = itemMapper.selectList(wrapper);
        if (productList.size() != ids.size()) {
            log.info("存在已下架的商品 {}", ids);
            throw new BusinessException(PRODUCT_DOWN);
        }
        return productList.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
    }
    
    @Override
    public void updateSaleNum(Map<Long, Integer> productNumMap) {
        // TODO
    }
    
    @Override
    public void updateSaleNum(Long id, Integer num) {
        // TODO
    }
    
    @Override
    public void updateSaleNum(List<String> orderNoList) {
        // TODO
    }
    
    @Override
    public List<ItemListVO> getPriorityItem(Long shopId) {
        int max = sysConfigApi.getInt(ConfigConstant.STORE_PRODUCT_MAX_RECOMMEND, 10);
        return itemMapper.getPriorityItem(shopId, max);
    }
    
    @Override
    public List<ItemListVO> getRecommend() {
        int max = sysConfigApi.getInt(ConfigConstant.PRODUCT_MAX_RECOMMEND, 10);
        return itemMapper.getRecommendItem(max);
    }
    
    @Override
    public List<ItemListVO> getByPage(ItemQueryDTO dto) {
        Page<ItemListVO> voPage = itemMapper.getByPage(dto.createPage(false), dto);
        return voPage.getRecords();
    }
    
    @Override
    public List<ItemListVO> getCouponScopeByPage(ItemCouponQueryDTO dto) {
        CouponConfig coupon = couponConfigMapper.selectById(dto.getCouponId());
        if (coupon == null) {
            log.error("优惠券不存在 [{}]", dto.getCouponId());
            throw new BusinessException(ErrorCode.COUPON_NOT_FOUND);
        }
        // 增加过滤条件,提高查询效率
        dto.setStoreId(coupon.getStoreId());
        dto.setUseScope(coupon.getUseScope());
        return itemMapper.getCouponScopeByPage(dto.createPage(false), dto).getRecords();
    }
    
    /**
     * 校验规格信息合法性
     * @param multiSpec 是否为多规格
     * @param specList 规格信息
     */
    private void checkSpec(Boolean multiSpec, List<ItemSpecRequest> specList) {
        if (Boolean.TRUE.equals(multiSpec)) {
            BeanValidator.validateList(specList);
            this.redoSpecValue(specList);
        }
    }
    
    /**
     * 同一家店铺 商品名称重复校验
     * @param productName 商品名称
     * @param id 商品id
     * @param storeId 店铺id
     */
    private void titleRedo(String productName, Long id, Long storeId) {
        LambdaQueryWrapper<Item> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Item::getTitle, productName);
        wrapper.ne(id != null, Item::getId, id);
        wrapper.eq(Item::getStoreId, storeId);
        Integer count = itemMapper.selectCount(wrapper);
        if (count > 0) {
            log.info("零售商品名称重复 [{}] [{}] [{}]", productName, id, storeId);
            throw new BusinessException(ErrorCode.PRODUCT_TITLE_REDO);
        }
    }
    
    /**
     * 规格值重复校验
     * @param specList 规格信息
     */
    private void redoSpecValue(List<ItemSpecRequest> specList) {
        if (specList.size() == 1) {
            return;
        }
        long size = specList.stream().map(ItemSpecRequest::getSpecValue).distinct().count();
        if (specList.size() != size) {
            throw new BusinessException(ErrorCode.SKU_TITLE_REDO);
        }
    }
    
    /**
     * 设置商品的最大值和最小值
     * @param item 商品信息
     * @param skuList 商品sku的信息
     */
    private void setMinMaxPrice(Item item, List<ItemSkuRequest> skuList) {
        if (CollUtil.isNotEmpty(skuList)) {
            OptionalInt max = skuList.stream().mapToInt(ItemSkuRequest::getSalePrice).max();
            if (max.isPresent()) {
                item.setMaxPrice(max.getAsInt());
            }
            OptionalInt min = skuList.stream().mapToInt(ItemSkuRequest::getSalePrice).min();
            if (min.isPresent()) {
                item.setMinPrice(min.getAsInt());
            }
        }
    }
}
