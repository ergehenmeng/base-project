package com.eghm.pay.impl;

import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.exception.WeChatPayException;
import com.eghm.pay.PayService;
import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.dto.RefundDTO;
import com.eghm.pay.enums.*;
import com.eghm.pay.vo.PayOrderVO;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.pay.vo.RefundVO;
import com.eghm.utils.DateUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * @author 二哥很猛
 */
@RequiredArgsConstructor
@Service("wechatPayService")
@Slf4j
public class WechatPayServiceImpl implements PayService {
    private WxPayService wxPayService;

    private final SystemProperties systemProperties;

    @Autowired(required = false)
    public void setWxPayService(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return tradeType.getPayChannel() == PayChannel.WECHAT;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        TradeTypeEnum transferType = transferType(dto.getTradeType());
        WxPayUnifiedOrderV3Request request = this.getWxPayUnifiedOrderV3Request(dto, transferType);
        WxPayUnifiedOrderV3Result result;
        try {
            result = wxPayService.unifiedOrderV3(transferType, request);
        } catch (Exception e) {
            log.error("微信支付下单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        return this.createStandardResult(dto.getTradeType(), result);
    }

    @Override
    public PayOrderVO queryOrder(String tradeNo) {
        WxPayOrderQueryV3Result result;
        try {
            result = wxPayService.queryOrderV3(null, tradeNo);
        } catch (Exception e) {
            log.error("微信订单查询失败 [{}]", tradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        PayOrderVO response = new PayOrderVO();
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
    public void closeOrder(String tradeNo) {
        try {
            wxPayService.closeOrderV3(tradeNo);
        } catch (Exception e) {
            log.error("微信订单关闭异常 [{}]", tradeNo, e);
        }
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        WxPayRefundV3Request request = getWxPayRefundV3Request(dto);
        WxPayRefundV3Result result;
        try {
            result = wxPayService.refundV3(request);
        } catch (Exception e) {
            log.error("微信退款申请失败 [{}]", dto.getRefundNo(), e);
            throw new BusinessException(ErrorCode.WECHAT_REFUND_APPLY);
        }
        return this.getRefundVO(result.getAmount().getPayerRefund(), result.getStatus(), result.getChannel(), result.getUserReceivedAccount(), result.getSuccessTime(), result.getCreateTime());
    }

    @Override
    public RefundVO queryRefund(String tradeNo, String refundNo) {
        WxPayRefundQueryV3Result result;
        try {
            result = wxPayService.refundQueryV3(refundNo);
        } catch (Exception e) {
            log.error("微信退款订单信息查询失败 [{}]", refundNo, e);
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        return this.getRefundVO(result.getAmount().getPayerRefund(), result.getStatus(), result.getChannel(), result.getUserReceivedAccount(), result.getSuccessTime(), result.getCreateTime());
    }

    @Override
    public WxPayNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header) {
        try {
            return wxPayService.parseOrderNotifyV3Result(notifyData, header);
        } catch (Exception e) {
            log.error("微信支付响应信息解析失败 [{}] [{}]", notifyData, header, e);
            throw new WeChatPayException(ErrorCode.NOTIFY_PAY_PARSE);
        }
    }

    @Override
    public WxPayRefundNotifyV3Result parseRefundNotify(String notifyData, SignatureHeader header) {
        try {
            return wxPayService.parseRefundNotifyV3Result(notifyData, header);
        } catch (Exception e) {
            log.error("微信退款响应信息解析失败 [{}] [{}]", notifyData, header, e);
            throw new WeChatPayException(ErrorCode.NOTIFY_REFUND_PARSE);
        }
    }

    @Override
    public void verifyNotify(Map<String, String> param) {
        log.error("微信不支持该接口 [{}]", param);
        throw new BusinessException(ErrorCode.NOT_SUPPORTED);
    }

    /**
     * 获取微信支付统一下单请求参数
     *
     * @param dto 原始请求参数
     * @param transferType 微信支付方式
     * @return 最终请求参数
     */
    private WxPayUnifiedOrderV3Request getWxPayUnifiedOrderV3Request(PrepayDTO dto, TradeTypeEnum transferType) {
        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(dto.getAmount());
        request.setAmount(amount);
        request.setAttach(dto.getAttach());
        request.setDescription(dto.getDescription());
        SystemProperties.WeChatProperties wechat = systemProperties.getWechat();
        request.setNotifyUrl(wechat.getPay().getNotifyHost() + CommonConstant.WECHAT_PAY_NOTIFY_URL);
        request.setOutTradeNo(dto.getTradeNo());
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(dto.getBuyerId());
        request.setPayer(payer);
        // H5支付额外包含一些东西, 同时不需要付款人信息
        if (transferType == TradeTypeEnum.H5) {
            WxPayUnifiedOrderV3Request.SceneInfo sceneInfo = new WxPayUnifiedOrderV3Request.SceneInfo();
            sceneInfo.setPayerClientIp(dto.getClientIp());
            WxPayUnifiedOrderV3Request.H5Info h5Info = new WxPayUnifiedOrderV3Request.H5Info();
            h5Info.setType("Wap");
            sceneInfo.setH5Info(h5Info);
            request.setSceneInfo(sceneInfo);
            request.setPayer(null);
        }
        return request;
    }

    /**
     * 组装退款请求参数
     *
     * @param dto 退款信息
     * @return 微信退款请求参数
     */
    @NotNull
    private WxPayRefundV3Request getWxPayRefundV3Request(RefundDTO dto) {
        SystemProperties.WeChatProperties wechat = systemProperties.getWechat();
        WxPayRefundV3Request request = new WxPayRefundV3Request();
        WxPayRefundV3Request.Amount amount = new WxPayRefundV3Request.Amount();
        amount.setRefund(dto.getAmount());
        amount.setTotal(dto.getTotal());
        amount.setCurrency("CNY");
        request.setAmount(amount);
        request.setNotifyUrl(wechat.getPay().getNotifyHost() + CommonConstant.WECHAT_REFUND_NOTIFY_URL);
        request.setOutTradeNo(dto.getTradeNo());
        request.setReason(dto.getReason());
        request.setOutRefundNo(dto.getRefundNo());
        return request;
    }

    /**
     * 返回标准支付参数
     *
     * @param tradeType 交易方式
     * @param result    微信支付信息
     * @return vo
     */
    private PrepayVO createStandardResult(TradeType tradeType, WxPayUnifiedOrderV3Result result) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = SignUtils.genRandomStr();
        PrepayVO response = new PrepayVO();
        response.setPayChannel(PayChannel.WECHAT);
        switch (tradeType) {
            case WECHAT_MINI, WECHAT_JSAPI:
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
     *
     * @param payerRefund           退款金额
     * @param status                退款状态
     * @param channel               退款渠道
     * @param memberReceivedAccount 退款返回账号
     * @param successTime           退款成功时间
     * @param createTime            退款受理时间
     * @return vo
     */
    private RefundVO getRefundVO(Integer payerRefund, String status, String channel, String memberReceivedAccount, String successTime, String createTime) {
        RefundVO vo = new RefundVO();
        vo.setAmount(payerRefund);
        vo.setState(RefundStatus.valueOf(status));
        vo.setChannel(RefundChannel.valueOf(channel));
        vo.setChannelAccount(memberReceivedAccount);
        vo.setSuccessTime(DateUtil.parseIso(successTime));
        vo.setCreateTime(DateUtil.parseIso(createTime));
        vo.setPayChannel(PayChannel.WECHAT);
        return vo;
    }

    /**
     * 转换交易方式
     *
     * @param tradeType 原交易方式
     * @return 新交易方式
     */
    private static TradeTypeEnum transferType(TradeType tradeType) {
        Optional<TradeTypeEnum> optional = Arrays.stream(TradeTypeEnum.values()).filter(typeEnum -> typeEnum.name().equals(tradeType.getCode())).findFirst();
        return optional.orElse(null);
    }
}
