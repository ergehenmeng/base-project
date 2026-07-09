package com.eghm.application.payment.service;

import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.PayNotifyMessage;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.domain.payment.enums.TradeType;
import com.eghm.application.payment.vo.PayOrderVO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;

import java.util.Map;

/**
 * @author 二哥很猛
 */
public interface PayService {

    /**
     * 交易方式是否支持
     *
     * @param tradeType 交易方式
     * @return true: 支持 false:不支持
     */
    default boolean supported(TradeType tradeType) {
        return false;
    }

    /**
     * 校验配置是否合法
     */
    default void checkConfig() {
    }

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
     * @param tradeNo 商户订单号
     * @return 订单信息
     */
    PayOrderVO queryOrder(String tradeNo);

    /**
     * 关闭订单号
     *
     * @param tradeNo 商户订单号
     */
    void closeOrder(String tradeNo);

    /**
     * 申请退款
     *
     * @param dto 退款信息
     * @return 退款相应信息
     */
    RefundVO applyRefund(RefundDTO dto);

    /**
     * 查询退款单号
     *
     * @param tradeNo  退款流水号
     * @param refundNo 退款流水号
     * @return 退款信息
     */
    RefundVO queryRefund(String tradeNo, String refundNo);

    /**
     * 解析支付异步通知
     *
     * @param notifyData 响应信息
     * @param header     头信息(用于校验)
     * @return 解析后的支付信息
     */
    PayNotifyMessage parsePayNotify(String notifyData, Map<String, String> header);

    /**
     * 解析退款异步通知
     *
     * @param notifyData 响应信息
     * @param header     头信息(用于校验)
     * @return 解析后的退款信息
     */
    PayNotifyMessage parseRefundNotify(String notifyData, Map<String, String> header);

    /**
     * 校验异步通知
     *
     * @param param 参数
     */
    void verifyNotify(Map<String, String> param);
}
