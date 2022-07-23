package com.eghm.model.dto.business.shopping;

import com.eghm.model.validation.annotation.RangeInt;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 购物车商品数量更新
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Data
public class CarQuantityDTO {

    @ApiModelProperty("购物车商品")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty("数量")
    @RangeInt(max = 99, message = "商品数量应为0~99")
    private Integer quantity;
}
