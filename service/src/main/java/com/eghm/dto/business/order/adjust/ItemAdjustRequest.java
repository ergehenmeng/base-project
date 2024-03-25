package com.eghm.dto.business.order.adjust;

import com.eghm.convertor.YuanToCentDecoder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Data
public class ItemAdjustRequest {

    @ApiModelProperty(value = "商品订单id")
    @NotNull(message = "请选择要改价的商品")
    private Long orderId;

    @ApiModelProperty(value = "新价格")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入新价格")
    private Integer price;
}
