package com.eghm.vo.business.item;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
public class ItemSkuVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "规格id,多个逗号分隔")
    private String specId;

    @ApiModelProperty(value = "划线价")
    private Integer linePrice;

    @ApiModelProperty(value = "销售价格")
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "sku图片(优先级最高)")
    private String skuPic;
}
