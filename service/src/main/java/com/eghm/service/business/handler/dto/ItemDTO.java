package com.eghm.service.business.handler.dto;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author wyb
 * @since 2023/6/14
 */
public class ItemDTO {

    @ApiModelProperty("商品id")
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @RangeInt(min = 1, max = 99, message = "购买数量应为1~99")
    @ApiModelProperty("商品数量,最大99")
    private Integer num;

    @ApiModelProperty("商品skuId")
    @NotNull(message = "规格信息不能为空")
    private Long skuId;
}
