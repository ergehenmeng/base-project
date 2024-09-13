package com.eghm.state.machine.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @since 2023/6/14
 */
@Data
public class SkuDTO {

    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99")
    @ApiModelProperty(value = "商品数量,最大99", required = true)
    private Integer num;

    @ApiModelProperty(value = "商品skuId", required = true)
    @NotNull(message = "规格信息不能为空")
    private Long skuId;
}
