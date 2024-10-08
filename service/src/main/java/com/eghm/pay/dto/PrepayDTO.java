package com.eghm.pay.dto;

import com.eghm.pay.enums.TradeType;
import lombok.Data;

/**
 * 预支付订单对象
 *
 * @author 二哥很猛
 */
@Data
public class PrepayDTO {

    /**
     * 微信公众号或小程序appId
     */
    private String appId;

    /**
     * 交易订单号
     */
    private String tradeNo;

    /**
     * 支付方式
     */
    private TradeType tradeType;

    /**
     * 订单描述信息
     */
    private String description;

    /**
     * 付款人buyerId (当面付为空)
     */
    private String buyerId;

    /**
     * 付款金额 单位:分
     */
    private Integer amount;

    /**
     * 附加信息
     */
    private String attach;

    /**
     * 付款人ip(微信H5必填)
     */
    private String clientIp;

    /**
     * 场景类型 iOS, Android, Wap (微信H5必填)
     */
    private String sceneType = "Wap";
}