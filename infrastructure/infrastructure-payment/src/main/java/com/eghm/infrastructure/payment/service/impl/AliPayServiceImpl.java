package com.eghm.infrastructure.payment.service.impl;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.eghm.infrastructure.integration.common.impl.SysConfigApi;
import com.eghm.configuration.ApplicationProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.AliPayException;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.payment.dto.PayNotifyMessage;
import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.domain.payment.enums.*;
import com.eghm.application.payment.service.CreatePayService;
import com.eghm.application.payment.service.PayService;
import com.eghm.application.payment.vo.PayOrderVO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;
import com.eghm.utils.DateUtil;
import com.eghm.utils.DecimalUtil;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Slf4j
@RequiredArgsConstructor
@Service("aliPayService")
public class AliPayServiceImpl implements PayService {

    /**
     * 退款成功
     */
    private static final String REFUND_SUCCESS = "REFUND_SUCCESS";

    private final SysConfigApi sysConfigApi;

    private final ApplicationProperties applicationProperties;

    private DefaultAlipayClient defaultAlipayClient;

    private final List<CreatePayService> createPayServiceList;

    @Autowired(required = false)
    public void setDefaultAlipayClient(DefaultAlipayClient defaultAlipayClient) {
        this.defaultAlipayClient = defaultAlipayClient;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return tradeType.getPayChannel() == PayChannel.ALIPAY;
    }

    @Override
    public void checkConfig() {
        if (defaultAlipayClient == null) {
            throw new BusinessException(ErrorCode.ALI_PAY_NOT_CONFIG);
        }
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        for (CreatePayService service : createPayServiceList) {
            if (service.supported(dto.getTradeType())) {
                return service.createPrepay(dto);
            }
        }
        log.error("不支持该支付方式 [{}]", dto.getTradeType());
        throw new BusinessException(ErrorCode.UNKNOWN_PAY_TYPE);
    }

    @Override
    public PayOrderVO queryOrder(String tradeNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(tradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝查询支付订单失败 [{}]", tradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        if (!response.isSuccess()) {
            log.error("支付宝支付订单查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        PayOrderVO vo = new PayOrderVO();
        vo.setPayerId(response.getBuyerUserId());
        vo.setAmount(DecimalUtil.yuanToCent(response.getTotalAmount()));
        vo.setTransactionId(response.getTradeNo());
        vo.setSuccessTime(DateUtil.convertDateTime(response.getSendPayDate()));
        vo.setTradeState(TradeState.of(response.getTradeStatus()));
        return vo;
    }

    @Override
    public void closeOrder(String tradeNo) {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(tradeNo);
        request.setBizModel(model);
        try {
            defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝关闭支付订单失败 [{}]", tradeNo, e);
        }
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_REFUND_NOTIFY_URL);
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(dto.getTradeNo());
        model.setOutRequestNo(dto.getRefundNo());
        model.setRefundReason(dto.getReason());
        model.setRefundAmount(DecimalUtil.centToYuan(dto.getAmount()));
        request.setBizModel(model);
        AlipayTradeRefundResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝退款申请发起失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.ALI_REFUND_APPLY);
        }
        if (!response.isSuccess()) {
            log.error("支付宝退款申请响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.ALI_REFUND_APPLY);
        }
        RefundVO vo = new RefundVO();
        vo.setChannel(RefundChannel.ORIGINAL);
        vo.setState(RefundStatus.PROCESSING);
        vo.setChannelAccount(response.getBuyerUserId());
        vo.setTotalAmount(DecimalUtil.yuanToCent(response.getRefundFee()));
        vo.setPayChannel(PayChannel.ALIPAY);
        return vo;
    }

    @Override
    public RefundVO queryRefund(String tradeNo, String refundNo) {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutRequestNo(refundNo);
        model.setOutTradeNo(tradeNo);
        model.setQueryOptions(Lists.newArrayList("gmt_refund_pay", "refund_detail_item_list"));
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}] [{}]", tradeNo, refundNo, e);
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        if (!response.isSuccess()) {
            log.error("支付宝退款状态查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        RefundVO vo = new RefundVO();
        vo.setSuccessTime(DateUtil.convertDateTime(response.getGmtRefundPay()));
        vo.setAmount(DecimalUtil.yuanToCent(response.getSendBackFee()));
        vo.setChannel(RefundChannel.ORIGINAL);
        if (REFUND_SUCCESS.equals(response.getRefundStatus())) {
            vo.setState(RefundStatus.SUCCESS);
        } else {
            log.warn("退款订单状态非成功 [{}] [{}] [{}]", tradeNo, refundNo, response.getRefundStatus());
            vo.setState(RefundStatus.ABNORMAL);
        }
        return vo;
    }

    @Override
    public PayNotifyMessage parsePayNotify(String notifyData, Map<String, String> header) {
        log.error("支付宝支付不支持该方法调用 [{}] [{}]", notifyData, header);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    @Override
    public PayNotifyMessage parseRefundNotify(String notifyData, Map<String, String> header) {
        log.error("支付宝退款不支持该方法调用 [{}] [{}]", notifyData, header);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    @Override
    public void verifyNotify(Map<String, String> param) {
        ApplicationProperties.AliPay pay = applicationProperties.getAli().getPay();
        boolean flag = false;
        try {
            flag = AlipaySignature.rsaCheckV1(param, pay.getPublicKey(), "UTF-8", "RSA2");
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}]", param, e);
        }
        if (!flag) {
            throw new AliPayException(ErrorCode.NOTIFY_SIGN_ERROR);
        }
    }

}
