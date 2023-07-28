package com.eghm.service.pay;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PayOrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;

import java.util.Map;

/**
 * @author 二哥很猛
 */
public interface PayService {

    /**
     * 交易方式是否支持
     * @param tradeType 交易方式
     * @return true: 支持 false:不支持
     */
    default boolean supported(TradeType tradeType) {
        return false;
    }

    /**
     * 生成预支付订单信息
     * @param dto 预支付信息
     * @return prepay_id
     */
    PrepayVO createPrepay(PrepayDTO dto);

    /**
     * 查询订单信息
     * @param outTradeNo 商户订单号
     * @return 订单信息
     */
    PayOrderVO queryOrder(String outTradeNo);

    /**
     * 关闭订单号
     * @param outTradeNo 商户订单号
     */
    void closeOrder(String outTradeNo);

    /**
     * 申请退款
     * @param dto 退款信息
     * @return 退款相应信息
     */
    RefundVO applyRefund(RefundDTO dto);

    /**
     * 查询退款单号
     * @param outTradeNo 退款流水号
     * @return 退款信息
     */
    RefundVO queryRefund(String outTradeNo, String outRefundNo);

    /**
     * 解析支付异步通知
     * @param notifyData 响应信息
     * @param header 头信息(用于校验)
     * @return 解析后的支付信息
     */
    WxPayOrderNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header);

    /**
     * 解析退款异步通知
     * @param notifyData 响应信息
     * @param header 头信息(用于校验)
     * @return 解析后的退款信息
     */
    WxPayRefundNotifyV3Result parseRefundNotify(String notifyData, SignatureHeader header);

    /**
     * 校验异步通知
     * @param param 参数
     */
    void verifyNotify(Map<String, String> param);
}