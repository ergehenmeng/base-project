package com.eghm.service.pay.dto;

import com.eghm.service.pay.enums.MerchantType;
import lombok.Data;

/**
 * 预支付订单对象
 * @author 二哥很猛
 */
@Data
public class PrepayDTO {

    /**
     * 订单描述信息
     */
    private String description;

    /**
     * 付款人openId
     */
    private String openId;

    /**
     * 付款金额 单位:分
     */
    private Integer amount;

    /**
     * 商户类型
     */
    private MerchantType merchantType;

    /**
     * 附加信息
     */
    private String attach;
}