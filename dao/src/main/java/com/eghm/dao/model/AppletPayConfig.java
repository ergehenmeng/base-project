package com.eghm.dao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class AppletPayConfig extends BaseEntity {

    /**
     * 唯一标识符<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : nid<br>
     */
    private String nid;

    /**
     * 微信小程序支付appId<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : app_id<br>
     */
    private String appId;

    /**
     * 商户号<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : merchant_id<br>
     */
    private String merchantId;

    /**
     * 订单过期时间 单位:秒<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : expire_time<br>
     */
    private Integer expireTime;

    /**
     * 订单异步通知地址<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : notify_url<br>
     */
    private String notifyUrl;

    /**
     * 订单号前缀(限英文)<br>
     * 表 : applet_pay_config<br>
     * 对应字段 : order_prefix<br>
     */
    private String orderPrefix;

}