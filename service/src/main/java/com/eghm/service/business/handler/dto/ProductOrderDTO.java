package com.eghm.service.business.handler.dto;

import com.eghm.dao.model.Product;
import com.eghm.dao.model.ProductSku;
import lombok.Data;

import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/9/5
 */
@Data
public class ProductOrderDTO {

    /**
     * 商品信息
     */
    private Map<Long, Product> productList;

    /**
     * 商品sku信息
     */
    private Map<Long, ProductSku> skuList;
}
