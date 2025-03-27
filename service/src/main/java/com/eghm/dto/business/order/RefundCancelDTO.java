package com.eghm.dto.business.order;

import com.eghm.annotation.Assign;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 目前支持取消退款的商品: 零售, 民宿, 线路
 *
 * @author wyb
 * @since 2023/6/1
 */
@Data
public class RefundCancelDTO {

    @ApiModelProperty("订单号")
    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "游客id(民宿/线路)")
    @NotNull(message = "请选择游客")
    private Long visitorId;

    @Assign
    @ApiModelProperty(value = "用户id", hidden = true)
    private Long memberId;
}
