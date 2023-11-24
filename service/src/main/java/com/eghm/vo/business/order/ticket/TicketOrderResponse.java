package com.eghm.vo.business.order.ticket;

import com.eghm.convertor.CentToYuanEncoder;
import com.eghm.enums.ref.CloseType;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wyb
 * @since 2023/6/8
 */
@Data
public class TicketOrderResponse {

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("门票名称")
    private String title;

    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty("支付方式")
    private PayType payType;

    @ApiModelProperty("购买数量")
    private Integer num;

    @ApiModelProperty("订单联系人")
    private String mobile;

    @ApiModelProperty(value = "订单状态")
    private OrderState state;

    @ApiModelProperty(value = "订单关闭方式")
    private CloseType closeType;

    @ApiModelProperty("付款金额")
    @JsonSerialize(using = CentToYuanEncoder.class)
    private Integer payAmount;

    @ApiModelProperty("支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
