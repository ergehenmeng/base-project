package com.eghm.service.business;

import com.eghm.model.dto.business.product.sku.ProductSkuRequest;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface ProductSkuService {

    /**
     * 新增特产sku信息
     * @param productId 产品id
     * @param skuList sku信息
     */
    void create(Long productId, List<ProductSkuRequest> skuList);

    /**
     * 更新sku信息
     * @param productId 商品id
     * @param skuList sku信息
     */
    void update(Long productId, List<ProductSkuRequest> skuList);

}
