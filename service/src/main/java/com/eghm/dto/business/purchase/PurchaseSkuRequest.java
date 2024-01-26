package com.eghm.dto.business.purchase;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/26
 */

@Data
public class PurchaseSkuRequest {

    @ApiModelProperty("skuId")
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty("销售价")
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty("限时价")
    @NotNull(message = "限时价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer limitPrice;
}
