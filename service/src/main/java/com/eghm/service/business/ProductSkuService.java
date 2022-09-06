package com.eghm.service.business;

import com.eghm.dao.model.ProductSku;
import com.eghm.model.dto.business.product.sku.ProductSkuRequest;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * 1: 删除旧sku
     * 2: 更新带id的sku
     * 3: 新增不带id的sku
     * @param productId 商品id
     * @param skuList sku信息
     */
    void update(Long productId, List<ProductSkuRequest> skuList);

    /**
     * 主键查询商品规格信息
     * @param skuId id
     * @return 规格信息
     */
    ProductSku selectById(Long skuId);

    /**
     * 主键查询商品规格信息(不存在则异常)
     * @param skuId id
     * @return 规格信息
     */
    ProductSku selectByIdRequired(Long skuId);

    /**
     * 根据id查询sku信息
     * @param ids  ids 多个id
     * @return 列表
     */
    Map<Long, ProductSku> getByIds(Set<Long> ids);

    /**
     * 更新sku库存
     * @param skuId skuId
     * @param num 正数+库存 负数-库存
     */
    void updateStock(Long skuId, Integer num);
}
