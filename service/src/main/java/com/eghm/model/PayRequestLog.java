package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.eghm.service.pay.enums.PayChannel;
import com.eghm.service.pay.enums.StepType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 支付或退款请求记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_request_log")
public class PayRequestLog extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @ApiModelProperty(value = "订单编号")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String orderNo;

    @ApiModelProperty(value = "请求类型 PAY: 支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @ApiModelProperty(value = "交易流水号")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String outTradeNo;

    @ApiModelProperty(value = "退款流水号")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String outRefundNo;

    @ApiModelProperty(value = "请求参数")
    private String requestBody;

    @ApiModelProperty(value = "响应参数")
    private String responseBody;

}
