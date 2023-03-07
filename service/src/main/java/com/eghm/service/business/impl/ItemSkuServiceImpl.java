package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.ItemSkuMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;
import com.eghm.service.business.ItemSkuService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
@Service("itemSkuService")
@Slf4j
@AllArgsConstructor
public class ItemSkuServiceImpl implements ItemSkuService {
    
    private final ItemSkuMapper itemSkuMapper;
    
    @Override
    public void insert(Item item, Map<String, Long> specMap, List<ItemSkuRequest> skuList) {
        for (ItemSkuRequest request : skuList) {
            ItemSku sku = DataUtil.copy(request, ItemSku.class, "id");
            sku.setItemId(item.getId());
            sku.setSpecId(this.getSpecId(specMap, request));
            itemSkuMapper.insert(sku);
        }
    }
    
    @Override
    public void update(Item item, Map<String, Long> specMap, List<ItemSkuRequest> skuList) {
        List<Long> skuIds = skuList.stream().map(ItemSkuRequest::getId).filter(Objects::nonNull).collect(Collectors.toList());
        this.deleteByNotIn(item.getId(), skuIds);
        for (ItemSkuRequest request : skuList) {
            ItemSku sku = DataUtil.copy(request, ItemSku.class);
            sku.setItemId(item.getId());
            sku.setSpecId(this.getSpecId(specMap, request));
            if (sku.getId() == null) {
                itemSkuMapper.insert(sku);
            } else {
                itemSkuMapper.updateById(sku);
            }
        }
    }
    
    @Override
    public List<ItemSku> getByItemId(Long itemId) {
        LambdaQueryWrapper<ItemSku> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ItemSku::getItemId, itemId);
        return itemSkuMapper.selectList(wrapper);
    }
    
    @Override
    public ItemSku selectByIdRequired(Long skuId) {
        ItemSku sku = itemSkuMapper.selectById(skuId);
        if (sku == null) {
            log.error("商品规格已删除 [{}]", skuId);
            throw new BusinessException(ErrorCode.SKU_DOWN);
        }
        return sku;
    }
    
    /**
     * 删除不在指定skuId列表的其他sku
     * @param itemId 商品id
     * @param skuIds skuIds
     */
    private void deleteByNotIn(Long itemId, List<Long> skuIds) {
        LambdaUpdateWrapper<ItemSku> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemSku::getItemId, itemId);
        wrapper.notIn(CollUtil.isNotEmpty(skuIds), ItemSku::getId, skuIds);
        itemSkuMapper.delete(wrapper);
    }
    
    /**
     * 组装sku所属的规格id, 多规格以逗号分隔
     * @param specMap 规格信息
     * @param request sku信息
     * @return 规格id
     */
    private String getSpecId(Map<String, Long> specMap, ItemSkuRequest request) {
        Long primaryId = specMap.get(request.getPrimarySpecValue());
        Long secondId = specMap.get(request.getSecondSpecValue());
        if (secondId == null) {
            return String.valueOf(primaryId);
        }
        return primaryId + "," + secondId;
    }
    
}
