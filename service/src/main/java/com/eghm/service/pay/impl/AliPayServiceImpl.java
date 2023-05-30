package com.eghm.service.pay.impl;

import cn.hutool.core.util.StrUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.*;
import com.eghm.configuration.SystemProperties;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.RefundChannel;
import com.eghm.service.pay.enums.RefundStatus;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import com.eghm.utils.DateUtil;
import com.eghm.utils.DecimalUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2022/7/24
 */
@Service("aliPayService")
@AllArgsConstructor
@Slf4j
public class AliPayServiceImpl implements PayService {

    /**
     * 退款成功
     */
    private static final String REFUND_SUCCESS = "REFUND_SUCCESS";

    private final SystemProperties systemProperties;

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        AlipayTradeCreateResponse response;
        try {
            SystemProperties.AliPayProperties aliPay = systemProperties.getAliPay();
            response = Factory.Payment.Common().optional("body", dto.getAttach()).asyncNotify(aliPay.getNotifyHost() + aliPay.getPayNotifyUrl())
                    .create(dto.getDescription(), dto.getOutTradeNo(), DecimalUtil.centToYuan(dto.getAmount()), dto.getBuyerId());
        } catch (Exception e) {
            log.error("支付宝创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (StrUtil.isNotBlank(response.getSubCode())) {
            log.error("支付宝下单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setTradeNo(response.tradeNo);
        return vo;
    }

    @Override
    public OrderVO queryOrder(String outTradeNo) {
        AlipayTradeQueryResponse response;
        try {
            response = Factory.Payment.Common().query(outTradeNo);
        } catch (Exception e) {
            log.error("支付宝查询支付订单失败 [{}]", outTradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        if (StrUtil.isNotBlank(response.getSubCode())) {
            log.error("支付宝支付订单查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }

        OrderVO vo = new OrderVO();
        vo.setAttach(response.getBody());
        vo.setPayerId(response.getBuyerUserId());
        vo.setAmount(DecimalUtil.yuanToCent(response.getTotalAmount()));
        vo.setTransactionId(response.getTradeNo());
        vo.setSuccessTime(DateUtil.parseLocalDateTime(response.getSendPayDate()));
        vo.setTradeState(TradeState.of(response.getTradeStatus()));
        vo.setTradeType(TradeType.ALI_PAY);
        return vo;
    }

    @Override
    public void closeOrder(String outTradeNo) {
        AlipayTradeCloseResponse response;
        try {
            response = Factory.Payment.Common().close(outTradeNo);
        } catch (Exception e) {
            log.error("支付宝关闭支付订单失败 [{}]", outTradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_CLOSE);
        }
        if (StrUtil.isNotBlank(response.getSubCode())) {
            log.error("支付宝关闭订单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.ORDER_CLOSE);
        }
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        AlipayTradeRefundResponse response;
        try {
            SystemProperties.AliPayProperties aliPay = systemProperties.getAliPay();
            response = Factory.Payment.Common()
                    .asyncNotify(aliPay.getNotifyHost() + aliPay.getRefundNotifyUrl())
                    .optional("out_request_no", dto.getOutRefundNo())
                    .optional("refund_reason", dto.getReason())
                    .refund(dto.getOutTradeNo(), DecimalUtil.centToYuan(dto.getAmount()));
        } catch (Exception e) {
            log.error("支付宝退款申请发起失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.REFUND_APPLY);
        }
        if (StrUtil.isNotBlank(response.getSubCode())) {
            log.error("支付宝退款申请响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.REFUND_APPLY);
        }
        RefundVO vo = new RefundVO();
        vo.setChannel(RefundChannel.ORIGINAL);
        vo.setState(RefundStatus.PROCESSING);
        vo.setChannelAccount(response.getBuyerUserId());
        vo.setTotalAmount(DecimalUtil.yuanToCent(response.getRefundFee()));
        return vo;
    }

    @Override
    public RefundVO queryRefund(String outTradeNo, String outRefundNo) {
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = Factory.Payment.Common().optional("query_options", Lists.newArrayList("gmt_refund_pay", "refund_detail_item_list")).queryRefund(outTradeNo, outRefundNo);
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}] [{}]", outTradeNo, outRefundNo, e);
            throw new BusinessException(ErrorCode.REFUND_APPLY);
        }
        if (StrUtil.isNotBlank(response.getSubCode())) {
            log.error("支付宝退款状态查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.REFUND_APPLY);
        }
        RefundVO vo = new RefundVO();
        vo.setSuccessTime(DateUtil.parseLocalDateTime(response.getGmtRefundPay()));
        vo.setAmount(DecimalUtil.yuanToCent(response.getSendBackFee()));
        vo.setChannel(RefundChannel.ORIGINAL);

        if (REFUND_SUCCESS.equals(response.getRefundStatus())) {
            vo.setState(RefundStatus.SUCCESS);
        } else {
            log.warn("退款订单状态非成功 [{}] [{}] [{}]", outTradeNo, outRefundNo, response.getRefundStatus());
            vo.setState(RefundStatus.ABNORMAL);
        }
        return vo;
    }

    @Override
    public WxPayOrderNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header) {
        log.error("支付宝支付不支持该方法调用 [{}] [{}]", notifyData, header);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    @Override
    public WxPayRefundNotifyV3Result parseRefundNotify(String notifyData, SignatureHeader header) {
        log.error("支付宝退款不支持该方法调用 [{}] [{}]", notifyData, header);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    @Override
    public void verifyNotify(Map<String, String> param) {
        boolean flag = false;
        try {
            log.info("支付宝验签原参数 [{}]", param);
            flag = Factory.Payment.Common().verifyNotify(param);
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}]", param, e);
        }
        if (!flag) {
            throw new BusinessException(ErrorCode.NOTIFY_SIGN_ERROR);
        }
    }
}
