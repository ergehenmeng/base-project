package com.eghm.model.vo.business.product;

import com.eghm.common.convertor.CentToYuanEncoder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2022/12/30
 */
@Data
public class ProductSkuResponse {

    @ApiModelProperty("id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "规格名称")
    private String title;

    @ApiModelProperty(value = "划线价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "成本价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer costPrice;

    @ApiModelProperty(value = "销售价")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty("封面图片")
    private String coverUrl;
}
