package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2024/9/10
 */

@Data
public class ItemSkuStockResponse {

    @ApiModelProperty(value = "sku_id")
    private Long id;

    @ApiModelProperty(value = "一级规格名")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名")
    private String secondSpecValue;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "剩余库存")
    private Integer stock;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

}
