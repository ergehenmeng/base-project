package com.eghm.model.dto.business.product.sku;

import com.eghm.common.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @date 2022/7/20
 */

@Data
public class ProductSkuRequest {

    @ApiModelProperty("skuId(编辑保存时有效)")
    private Long id;

    @ApiModelProperty(value = "规格名称")
    @Size(min = 1, max = 20, message = "规格名称长度1~20位")
    @NotBlank(message = "规格名称不能为空")
    private String title;

    @ApiModelProperty(value = "划线价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer linePrice;

    @ApiModelProperty(value = "成本价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer costPrice;

    @ApiModelProperty(value = "销售价")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "销售价不能为空")
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    @NotNull(message = "库存不能为空")
    @Min(value = 0, message = "库存不能小于0")
    private Integer stock;

    @ApiModelProperty("封面图片")
    private String coverUrl;
}
