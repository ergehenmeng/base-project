package com.eghm.service.business;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
public interface ItemSkuService {
    
    /**
     * 插入sku列表
     * @param item 商品信息
     * @param specMap 规格信息
     * @param skuList sku信息
     */
    void insert(Item item, Map<String, Long> specMap, List<ItemSkuRequest> skuList);
    
    /**
     * 更新sku信息
     * @param item 商品信息
     * @param specMap 规格信息
     * @param skuList sku信息
     */
    void update(Item item, Map<String, Long> specMap, List<ItemSkuRequest> skuList);
    
    /**
     * 根据商品id查询sku信息
     * @param itemId 商品id
     * @return sku列表
     */
    List<ItemSku> getByItemId(Long itemId);
    
    /**
     * 主键查询商品规格信息(不存在则异常)
     * @param skuId id
     * @return 规格信息
     */
    ItemSku selectByIdRequired(Long skuId);
    
    /**
     * 更新sku库存
     * @param skuId skuId
     * @param num 正数+库存 负数-库存
     */
    void updateStock(Long skuId, Integer num);
    
    /**
     * 批量修改sku的库存
     * @param map key=skuId, value=num (正数+库存 负数-库存)
     */
    void updateStock(Map<Long, Integer> map);
    
    /**
     * 主键查询商品sku信息
     * @param ids ids
     * @return sku
     */
    Map<Long, ItemSku> getByIds(Set<Long> ids);
}
