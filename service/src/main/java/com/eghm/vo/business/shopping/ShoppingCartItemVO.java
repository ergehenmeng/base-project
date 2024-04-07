package com.eghm.vo.business.shopping;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/9/27
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingCartItemVO {

    @ApiModelProperty("店铺名称")
    @JsonIgnore
    private String storeTitle;

    @ApiModelProperty("店铺id")
    @JsonIgnore
    private Long storeId;

    @ApiModelProperty("购物车id")
    private Long id;

    @ApiModelProperty("商品图片")
    private String coverUrl;

    @ApiModelProperty("商品名称")
    private String title;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("加购时价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer rawSalePrice;

    @ApiModelProperty("商品限购数量")
    private Integer quota;

    @ApiModelProperty("规格名称")
    private String skuName;

    @ApiModelProperty("规格库存数")
    private Integer stock;

    @ApiModelProperty("sku状态 1:已上架 0/2:已下架")
    private Integer skuState;
}
