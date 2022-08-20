package com.eghm.common.enums.ref;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 二哥很猛
 * @date 2022/7/13
 */
@Getter
@AllArgsConstructor
public enum ProductType implements IEnum<String> {

    /**
     * 景区门票
     */
    TICKET("ticket", "门票", "MP", "defaultApplyRefundHandler", "defaultAuditRefundHandler", "defaultOrderCancelHandler", "defaultPayNotifyHandler","defaultRefundNotifyHandler"),

    /**
     * 餐饮券
     */
    VOUCHER("voucher", "餐饮券", "CY", "defaultApplyRefundHandler", "defaultAuditRefundHandler", "defaultOrderCancelHandler", "defaultPayNotifyHandler","defaultRefundNotifyHandler"),

    /**
     * 民宿
     */
    HOMESTAY("homestay", "民宿", "MS", "defaultApplyRefundHandler", "defaultAuditRefundHandler", "defaultOrderCancelHandler", "defaultPayNotifyHandler","defaultRefundNotifyHandler"),

    /**
     * 商品(文创/特产)
     */
    PRODUCT("product", "商品", "SP", "defaultApplyRefundHandler", "defaultAuditRefundHandler", "defaultOrderCancelHandler", "defaultPayNotifyHandler","defaultRefundNotifyHandler"),

    ;

    /**
     * 值
     */
    private final String value;

    /**
     * 名称
     */
    private final String name;

    /**
     * 前缀
     */
    private final String  prefix;

    /**
     * 申请退款bean
     */
    private final String applyRefundBean;

    /**
     * 审核退款bean
     */
    private final String auditRefundBean;

    /**
     * 订单取消bean
     */
    private final String orderCancelBean;

    /**
     * 订单支付异步处理bean
     */
    private final String payNotifyBean;

    /**
     * 退款异步处理bean
     */
    private final String refundNotifyBean;

    @Override
    public String getValue() {
        return value;
    }
}
