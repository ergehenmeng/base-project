package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@TableName("applet_pay_config")
public class AppletPayConfig extends BaseEntity {

    @ApiModelProperty("唯一标识符")
    private String nid;

    @ApiModelProperty("微信小程序支付appId")
    private String appId;

    @ApiModelProperty("商户号")
    private String merchantId;

    @ApiModelProperty("订单过期时间 单位:秒")
    private Integer expireTime;

    @ApiModelProperty("订单异步通知地址")
    private String notifyUrl;

    @ApiModelProperty("订单号前缀(限英文)")
    private String orderPrefix;

}