package com.eghm.service.business;

import com.eghm.model.Item;
import com.eghm.model.dto.business.product.ItemAddRequest;
import com.eghm.model.dto.business.product.ItemEditRequest;
import com.eghm.model.vo.business.item.ItemResponse;

/**
 * @author 殿小二
 * @date 2023/3/6
 */
public interface ItemService {
    
    /**
     * 创建零售商品
     * @param request 商品信息
     */
    void create(ItemAddRequest request);
    
    /**
     * 更新零售商品
     * @param request 商品信息
     */
    void update(ItemEditRequest request);
    
    /**
     * 查询零售商品详情
     * @param itemId 商品id
     * @return 详情 包含sku spec等信息
     */
    ItemResponse getDetailById(Long itemId);
    
    /**
     * 主键查询零售商品
     * @param itemId id
     * @return 零售
     */
    Item selectById(Long itemId);
    
    /**
     * 主键查询零售商品, 为空报错
     * @param itemId id
     * @return 零售
     */
    Item selectByIdRequired(Long itemId);
}
