package com.eghm.service.pay;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.response.OrderResponse;
import com.eghm.service.pay.response.PrepayResponse;

/**
 * @author 二哥很猛
 */
public interface AggregatePayService {

    /**
     * 生成预支付订单信息
     * @param dto 预支付信息
     * @return prepay_id
     */
    PrepayResponse createPrepay(PrepayDTO dto);

    /**
     * 查询订单信息
     * @param tradeType 交易方式
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    OrderResponse queryOrder(TradeType tradeType, String outTradeNo);
}