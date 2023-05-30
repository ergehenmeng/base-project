package com.eghm.service.pay.impl;

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
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author 二哥很猛
 */
@Service("wechatPayService")
@Slf4j
public class WechatPayServiceImpl implements PayService {

    private final WxPayService wxPayService;

    private final SystemProperties systemProperties;

    public WechatPayServiceImpl(@Autowired(required = false) WxPayService wxPayService, SystemProperties systemProperties) {
        this.wxPayService = wxPayService;
        this.systemProperties = systemProperties;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return transferType(tradeType) != null;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        TradeTypeEnum transferType = transferType(dto.getTradeType());
        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(dto.getAmount());
        request.setAmount(amount);
        request.setAttach(dto.getAttach());
        request.setDescription(dto.getDescription());
        SystemProperties.WeChatProperties wechat = systemProperties.getWechat();
        request.setNotifyUrl(wechat.getNotifyHost() + wechat.getPayNotifyUrl());
        request.setOutTradeNo(dto.getOutTradeNo());
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(dto.getBuyerId());
        request.setPayer(payer);
        WxPayUnifiedOrderV3Result result;
        try {
            result = wxPayService.unifiedOrderV3(transferType, request);
        } catch (WxPayException e) {
            log.error("微信支付下单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        return this.createStandardResult(dto.getTradeType(), result);
    }

    @Override
    public OrderVO queryOrder(String outTradeNo) {
        WxPayOrderQueryV3Result result;
        try {
            result = wxPayService.queryOrderV3(null, outTradeNo);
        } catch (WxPayException e) {
            log.error("微信订单查询失败 [{}]", outTradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        OrderVO response = new OrderVO();
        response.setAmount(result.getAmount().getPayerTotal());
        response.setAttach(result.getAttach());
        response.setSuccessTime(DateUtil.parseIso(result.getSuccessTime()));
        response.setTradeType(TradeType.of(result.getTradeType()));
        response.setTradeState(TradeState.of(result.getTradeState()));
        response.setPayerId(result.getPayer().getOpenid());
        response.setTransactionId(result.getTransactionId());
        return response;
    }

    @Override
    public void closeOrder(String outTradeNo) {
        try {
            wxPayService.closeOrderV3(outTradeNo);
        } catch (WxPayException e) {
            log.error("微信订单关闭异常 [{}]", outTradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_CLOSE);
        }
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        SystemProperties.WeChatProperties wechat = systemProperties.getWechat();
        WxPayRefundV3Request request = new WxPayRefundV3Request();
        WxPayRefundV3Request.Amount amount = new WxPayRefundV3Request.Amount();
        amount.setRefund(dto.getAmount());
        amount.setTotal(dto.getTotal());
        request.setAmount(amount);
        request.setNotifyUrl(wechat.getNotifyHost() + wechat.getRefundNotifyUrl());
        request.setOutTradeNo(dto.getOutTradeNo());
        request.setReason(dto.getReason());
        request.setOutRefundNo(dto.getOutRefundNo());
        WxPayRefundV3Result result;
        try {
            result = wxPayService.refundV3(request);
        } catch (WxPayException e) {
            log.error("微信退款申请失败 [{}]", dto.getOutRefundNo(), e);
            throw new BusinessException(ErrorCode.REFUND_APPLY);
        }
        return this.getRefundVO(result.getAmount().getPayerRefund(), result.getStatus(), result.getChannel(), result.getUserReceivedAccount(), result.getSuccessTime(), result.getCreateTime());
    }

    @Override
    public RefundVO queryRefund(String outTradeNo, String outRefundNo) {
        WxPayRefundQueryV3Result result;
        try {
            result = wxPayService.refundQueryV3(outRefundNo);
        } catch (WxPayException e) {
            log.error("微信退款订单信息查询失败 [{}]", outRefundNo, e);
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        return this.getRefundVO(result.getAmount().getPayerRefund(), result.getStatus(), result.getChannel(), result.getUserReceivedAccount(), result.getSuccessTime(), result.getCreateTime());
    }

    @Override
    public WxPayOrderNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header) {
        log.info("微信支付异步通知源数据 [{}] [{}]", notifyData, header);
        try {
            return wxPayService.parseOrderNotifyV3Result(notifyData, header);
        } catch (WxPayException e) {
            log.error("微信支付响应信息解析失败 [{}] [{}]", notifyData, header, e);
            throw new BusinessException(ErrorCode.NOTIFY_PAY_PARSE);
        }
    }

    @Override
    public WxPayRefundNotifyV3Result parseRefundNotify(String notifyData, SignatureHeader header) {
        log.info("微信退款异步通知源数据 [{}] [{}]", notifyData, header);
        try {
            return wxPayService.parseRefundNotifyV3Result(notifyData, header);
        } catch (WxPayException e) {
            log.error("微信退款响应信息解析失败 [{}] [{}]", notifyData, header, e);
            throw new BusinessException(ErrorCode.NOTIFY_REFUND_PARSE);
        }
    }

    @Override
    public void verifyNotify(Map<String, String> param) {
        log.error("微信不支持该接口 [{}]", param);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    /**
     * 返回标准支付参数
     * @param tradeType 交易方式
     * @param result 微信支付信息
     * @return vo
     */
    private PrepayVO createStandardResult(TradeType tradeType, WxPayUnifiedOrderV3Result result) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = SignUtils.genRandomStr();
        PrepayVO response = new PrepayVO();
        switch (tradeType) {
            case WECHAT_MINI:
            case WECHAT_JSAPI:
                response.setTimeStamp(timestamp);
                response.setPackageValue("prepay_id=" + result.getPrepayId());
                response.setNonceStr(nonceStr);
                response.setSignType("RSA");
                response.setPaySign(SignUtils.sign(String.format("%s%n%s%n%s%n%s%n", wxPayService.getConfig().getAppId(), timestamp, nonceStr, response.getPackageValue()), wxPayService.getConfig().getPrivateKey()));
                return response;
            case WECHAT_H5:
                response.setH5Url(result.getH5Url());
                return response;
            case WECHAT_NATIVE:
                response.setQrCodeUrl(result.getCodeUrl());
                return response;
            case WECHAT_APP:
                response.setPrepayId(result.getPrepayId());
                response.setPartnerId(wxPayService.getConfig().getMchId());
                response.setTimeStamp(timestamp);
                response.setNonceStr(nonceStr);
                response.setPackageValue("Sign=WXPay");
                return response;
            default:
                throw new BusinessException(ErrorCode.UNKNOWN_PAY_TYPE);
        }
    }

    /**
     * 退款信息组装
     * @param payerRefund 退款金额
     * @param status 退款状态
     * @param channel 退款渠道
     * @param userReceivedAccount 退款返回账号
     * @param successTime 退款成功时间
     * @param createTime 退款受理时间
     * @return vo
     */
    private RefundVO getRefundVO(Integer payerRefund, String status, String channel, String userReceivedAccount, String successTime, String createTime) {
        RefundVO vo = new RefundVO();
        vo.setAmount(payerRefund);
        vo.setState(RefundStatus.valueOf(status));
        vo.setChannel(RefundChannel.valueOf(channel));
        vo.setChannelAccount(userReceivedAccount);
        vo.setSuccessTime(DateUtil.parseIso(successTime));
        vo.setCreateTime(DateUtil.parseIso(createTime));
        return vo;
    }

    /**
     * 转换交易方式
     * @param tradeType 原交易方式
     * @return 新交易方式
     */
    private static TradeTypeEnum transferType(TradeType tradeType) {
        Optional<TradeTypeEnum> optional = Arrays.stream(TradeTypeEnum.values()).filter(typeEnum -> typeEnum.name().equals(tradeType.getName())).findFirst();
        return optional.orElse(null);
    }

}
