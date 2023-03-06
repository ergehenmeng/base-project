package com.eghm.service.business;

import com.eghm.model.dto.business.product.ItemAddRequest;
import com.eghm.model.dto.business.product.ItemEditRequest;

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
}
