package com.eghm.vo.business.item;

import com.eghm.convertor.BigDecimalOmitEncoder;
import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 殿小二
 * @since 2023/3/7
 */

@Data
public class ItemSkuResponse {

    @ApiModelProperty("id(编辑时不能为空)")
    private Long id;

    @ApiModelProperty(value = "一级规格名")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名(单规格为空)")
    private String secondSpecValue;

    @ApiModelProperty(value = "一级规格值数量(方便前端设置单元格)")
    private String specIds;

    @ApiModelProperty(value = "二级规格值数量(方便前端设置单元格)")
    private Integer secondSize;

    @ApiModelProperty(value = "成本价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer costPrice;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "虚拟销量")
    private Integer virtualNum;

    @ApiModelProperty(value = "sku图片(优先级最高)")
    private String skuPic;

    @ApiModelProperty("重量")
    @JsonSerialize(using = BigDecimalOmitEncoder.class)
    private BigDecimal weight;

}
