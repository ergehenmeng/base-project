package com.eghm.vo.business.item;

import com.eghm.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 二哥很猛
 * @since 2023/8/30
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemSkuVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "规格id,多个逗号分隔")
    private String specId;

    @ApiModelProperty(value = "一级规格名")
    private String primarySpecValue;

    @ApiModelProperty(value = "二级规格名")
    private String secondSpecValue;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "销售价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty("拼团或限时购价格")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer discountPrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "销售量")
    private Integer saleNum;

    @ApiModelProperty("重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "sku图片(优先级最高)")
    private String skuPic;
}
