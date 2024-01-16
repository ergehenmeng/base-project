package com.eghm.service.pay;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PayOrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;

/**
 * @author 二哥很猛
 */
public interface AggregatePayService {

    /**
     * 生成预支付订单信息
     *
     * @param dto 预支付信息
     * @return prepay_id
     */
    PrepayVO createPrepay(PrepayDTO dto);

    /**
     * 查询订单信息
     *
     * @param tradeType  交易方式
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    PayOrderVO queryOrder(TradeType tradeType, String outTradeNo);

    /**
     * 关闭订单号
     *
     * @param tradeType  交易类型
     * @param outTradeNo 商户订单号
     */
    void closeOrder(TradeType tradeType, String outTradeNo);

    /**
     * 申请退款
     *
     * @param dto 退款信息
     */
    void applyRefund(RefundDTO dto);

    /**
     * 查询退款单号
     *
     * @param tradeType   交易类型
     * @param outTradeNo  交易流水号 (支付宝必填)
     * @param outRefundNo 退款流水号
     * @return 退款信息
     */
    RefundVO queryRefund(TradeType tradeType, String outTradeNo, String outRefundNo);
}