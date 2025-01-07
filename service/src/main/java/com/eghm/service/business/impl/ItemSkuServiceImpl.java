package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.item.ItemStockRequest;
import com.eghm.dto.business.item.sku.ItemSkuRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ItemSkuMapper;
import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.service.business.ItemSkuService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.item.ItemSkuVO;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.eghm.enums.ErrorCode.SKU_DOWN;
import static com.eghm.enums.ErrorCode.SKU_STOCK;

/**
 * @author 殿小二
 * @since 2023/3/6
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
            sku.setSpecIds(this.getSpecId(specMap, request));
            itemSkuMapper.insert(sku);
        }
    }

    @Override
    public void update(Item item, Map<String, Long> specMap, List<ItemSkuRequest> skuList) {
        List<Long> skuIds = skuList.stream().map(ItemSkuRequest::getId).filter(Objects::nonNull).collect(Collectors.toList());
        LambdaUpdateWrapper<ItemSku> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(ItemSku::getItemId, item.getId());
        wrapper.notIn(CollUtil.isNotEmpty(skuIds), ItemSku::getId, skuIds);
        itemSkuMapper.delete(wrapper);
        for (ItemSkuRequest request : skuList) {
            ItemSku sku = DataUtil.copy(request, ItemSku.class);
            sku.setItemId(item.getId());
            sku.setSpecIds(this.getSpecId(specMap, request));
            if (sku.getId() == null) {
                itemSkuMapper.insert(sku);
            } else {
                itemSkuMapper.updateById(sku);
            }
        }
    }

    @Override
    public List<ItemSkuVO> getByItemId(Long itemId) {
        List<ItemSku> skuList = this.getSkuList(itemId);
        return DataUtil.copy(skuList, ItemSkuVO.class);
    }

    @Override
    public List<ItemSku> getSkuList(Long itemId) {
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

    @Override
    public void updateStock(Long skuId, Integer num) {
        int stock = itemSkuMapper.updateStock(skuId, num);
        if (stock != 1) {
            log.error("商品更新库存失败 [{}] [{}] [{}]", skuId, num, stock);
            throw new BusinessException(SKU_STOCK);
        }
    }

    @Override
    public void updateStock(Map<Long, Integer> map) {
        for (Map.Entry<Long, Integer> entry : map.entrySet()) {
            this.updateStock(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void addStock(List<ItemStockRequest> skuList, Long itemId) {
        if (CollUtil.isEmpty(skuList)) {
            return;
        }
        skuList.forEach(itemAddStockRequest -> itemSkuMapper.addStock(itemAddStockRequest.getId(), itemAddStockRequest.getNum(), itemId));
    }

    @Override
    public Map<Long, ItemSku> getByIdShelveMap(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Maps.newHashMapWithExpectedSize(1);
        }
        List<ItemSku> skuList = itemSkuMapper.selectByIds(ids);
        if (skuList.size() != ids.size()) {
            log.info("存在已下架的商品规格 {}", ids);
            throw new BusinessException(SKU_DOWN);
        }
        return skuList.stream().collect(Collectors.toMap(ItemSku::getId, Function.identity()));
    }

    @Override
    public Integer calcTotalNum(Long itemId) {
        return itemSkuMapper.calcTotalNum(itemId);
    }

    /**
     * 组装sku所属的规格id, 多规格以逗号分隔
     *
     * @param specMap 规格信息
     * @param request sku信息
     * @return 规格id
     */
    private String getSpecId(Map<String, Long> specMap, ItemSkuRequest request) {
        Long primaryId = specMap.get(request.getPrimarySpecValue());
        Long secondId = specMap.get(request.getSecondSpecValue());
        if (secondId == null) {
            if (primaryId == null) {
                return null;
            }
            return String.valueOf(primaryId);
        }
        return primaryId + "," + secondId;
    }

}
