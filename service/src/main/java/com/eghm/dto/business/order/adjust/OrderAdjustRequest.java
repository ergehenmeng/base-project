package com.eghm.dto.business.order.adjust;

import com.eghm.convertor.YuanToCentDecoder;
import com.eghm.dto.ext.ActionRecord;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderAdjustRequest extends ActionRecord {

    @ApiModelProperty(value = "订单编号")
    @NotBlank(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "商品订单id")
    @NotNull(message = "请选择要改价的商品")
    private Long id;

    @ApiModelProperty(value = "新价格")
    @JsonDeserialize(using = YuanToCentDecoder.class)
    @NotNull(message = "请输入新价格")
    private Integer price;

}
