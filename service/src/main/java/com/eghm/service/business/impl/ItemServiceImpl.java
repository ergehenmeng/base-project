package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.ItemMapper;
import com.eghm.model.Item;
import com.eghm.model.dto.business.product.ItemAddRequest;
import com.eghm.model.dto.business.product.ItemEditRequest;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;
import com.eghm.model.dto.business.product.sku.ItemSpecRequest;
import com.eghm.service.business.ItemService;
import com.eghm.service.business.ItemSkuService;
import com.eghm.service.business.ItemSpecService;
import com.eghm.utils.BeanValidator;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

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
