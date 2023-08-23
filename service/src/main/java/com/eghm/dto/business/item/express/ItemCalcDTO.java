package com.eghm.dto.business.item.express;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2023/8/23
 */
@Data
public class ItemCalcDTO {

    @ApiModelProperty("商品ID")
    @NotNull(message = "请选择商品")
    private Long itemId;

    @ApiModelProperty("skuId")
    @NotNull(message = "请选择商品规格")
    private Long skuId;

    @ApiModelProperty("数量")
    @Min(value = 1, message = "购买数量必须大于等于1")
    @NotNull(message = "商品数量不能为空")
    private Integer num;
}
