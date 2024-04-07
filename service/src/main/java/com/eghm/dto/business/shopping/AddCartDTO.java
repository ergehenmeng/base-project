package com.eghm.dto.business.shopping;

import com.eghm.annotation.Assign;
import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.validation.annotation.RangeInt;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Data
public class AddCartDTO {

    @ApiModelProperty(value = "商品id", required = true)
    @NotNull(message = "商品不能为空")
    private Long itemId;

    @ApiModelProperty(value = "商品规格id", required = true)
    @NotNull(message = "规格不能为空")
    private Long skuId;

    @ApiModelProperty(value = "商品售价(冗余)")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    private Integer salePrice;

    @ApiModelProperty(value = "数量", required = true)
    @RangeInt(min = 1, max = 99, message = "数量必须1~99之间")
    private Integer quantity;

    @Assign
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long memberId;
}
