package com.eghm.vo.business.shopping;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartItemVO {

    @Schema(description = "店铺名称")
    @JsonIgnore
    private String storeTitle;

    @Schema(description = "店铺id")
    @JsonIgnore
    private Long storeId;

    @Schema(description = "购物车id")
    private Long id;

    @Schema(description = "商品图片")
    private String coverUrl;

    @Schema(description = "商品名称")
    private String title;

    @Schema(description = "数量")
    private Integer quantity;

    @Schema(description = "售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @Schema(description = "加购时价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer rawSalePrice;

    @Schema(description = "商品限购数量")
    private Integer quota;

    @Schema(description = "规格名称")
    private String skuName;

    @Schema(description = "规格库存数")
    private Integer stock;

    @Schema(description = "sku状态 1:已上架 0/2:已下架")
    private Integer skuState;
}
