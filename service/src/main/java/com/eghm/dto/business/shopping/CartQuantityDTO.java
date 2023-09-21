package com.eghm.dto.business.shopping;

import com.eghm.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车商品数量更新
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Data
public class CartQuantityDTO {

    @ApiModelProperty(value = "购物车商品", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "数量", required = true)
    @RangeInt(max = 99, message = "商品数量应为0~99")
    private Integer quantity;
}
