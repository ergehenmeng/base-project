package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.handler.mysql.LikeTypeHandler;
import com.eghm.service.pay.enums.PayChannel;
import com.eghm.service.pay.enums.StepType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 支付异步通知记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("pay_notify_log")
public class PayNotifyLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "交易方式 WECHAT:微信 ALIPAY:支付宝")
    private PayChannel payChannel;

    @ApiModelProperty("异步通知唯一id")
    private String notifyId;

    @ApiModelProperty(value = "通知类型 PAY: 支付异步通知 REFUND:退款异步通知")
    private StepType stepType;

    @ApiModelProperty(value = "交易流水号")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String outTradeNo;

    @ApiModelProperty(value = "退款流水号")
    @TableField(typeHandler = LikeTypeHandler.class)
    private String outRefundNo;

    @ApiModelProperty(value = "退款通知原始参数")
    private String params;

}
