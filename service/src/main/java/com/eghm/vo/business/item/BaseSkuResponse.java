package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
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

    @ApiModelProperty("skuId")
    private Long skuId;

    @ApiModelProperty(value = "一级规格名(单规格为空)")
    @JsonIgnore
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名(单规格为空)")
    @JsonIgnore
    private String secondSpecValue;

    @ApiModelProperty(value = "规格名")
    private String specValue;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "规格图片(可能为空)")
    @JsonIgnore
    private String specPic;

    @ApiModelProperty(value = "sku图片(可能为空)")
    private String skuPic;
}
