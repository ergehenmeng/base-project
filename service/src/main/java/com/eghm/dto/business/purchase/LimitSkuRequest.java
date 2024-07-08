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
public class LimitSkuRequest {

    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品id不能为空")
    private Long itemId;

    @ApiModelProperty(value = "skuId", required = true)
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    @ApiModelProperty(value = "销售价", required = true)
    @NotNull(message = "销售价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "限时价", required = true)
    @NotNull(message = "限时价不能为空")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer discountPrice;
}
