package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/1/29
 */

@Data
public class BaseSkuResponse {

    @ApiModelProperty(value = "商品id(冗余)")
    private Long itemId;

    @ApiModelProperty(value = "商品名称(冗余)")
    private String title;

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty(value = "商品规格数量(冗余)")
    private Integer skuSize;

    @ApiModelProperty(value = "一级规格名(单规格为空)")
    @JsonIgnore
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名(单规格为空)")
    @JsonIgnore
    private String secondSpecValue;

    @ApiModelProperty(value = "规格名(单规格为空)")
    private String specValue;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanSerializer.class)
    private Integer salePrice;

    @ApiModelProperty(value = "规格图片(可能为空)")
    @JsonIgnore
    private String specPic;

    @ApiModelProperty(value = "sku图片(可能为空)")
    private String skuPic;
}
