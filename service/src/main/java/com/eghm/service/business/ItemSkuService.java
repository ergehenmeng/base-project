package com.eghm.service.business;

import com.eghm.model.Item;
import com.eghm.model.ItemSku;
import com.eghm.model.dto.business.product.sku.ItemSkuRequest;

import java.util.List;
import java.util.Map;

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
}
