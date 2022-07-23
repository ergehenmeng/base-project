package com.eghm.service.pay.impl;

import cn.hutool.core.date.DateUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.response.OrderResponse;
import com.eghm.service.pay.response.PrepayResponse;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author 二哥很猛
 */
@Service
@Slf4j
@AllArgsConstructor
public class WechatPayServiceImpl implements PayService {

    private final WxPayService wxPayService;

    @Override
    public boolean supported(TradeType tradeType) {
        return transferType(tradeType) != null;
    }

    @Override
    public PrepayResponse createPrepay(PrepayDTO dto) {
        TradeTypeEnum transferType = transferType(dto.getTradeType());
        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(dto.getAmount());
        request.setAmount(amount);
        request.setAttach(dto.getAttach());
        request.setDescription(dto.getDescription());
        request.setNotifyUrl(dto.getNotifyUrl());
        request.setOutTradeNo(dto.getOutTradeNo());
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(dto.getOpenId());
        request.setPayer(payer);
        WxPayUnifiedOrderV3Result result;
        try {
            result = wxPayService.unifiedOrderV3(transferType, request);
        } catch (WxPayException e) {
            log.error("微信支付下单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }

        String timestamp = String.valueOf(DateUtil.currentSeconds());
        String nonceStr = SignUtils.genRandomStr();
        PrepayResponse response = new PrepayResponse();
        switch (dto.getTradeType()) {
            case WECHAT_MINI:
            case WECHAT_JSAPI:
                response.setTimeStamp(timestamp);
                response.setPackageValue("prepay_id=" + result.getPrepayId());
                response.setNonceStr(nonceStr);
                response.setSignType("RSA");
                response.setPaySign(SignUtils.sign(String.format("%s\n%s\n%s\n%s\n", wxPayService.getConfig().getAppId(), timestamp, nonceStr, response.getPackageValue()), wxPayService.getConfig().getPrivateKey()));
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

    @Override
    public OrderResponse queryOrder(String outTradeNo) {
        WxPayOrderQueryV3Result result;
        try {
            result = wxPayService.queryOrderV3(null, outTradeNo);
        } catch (WxPayException e) {
            log.error("微信订单查询失败 [{}]", outTradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        OrderResponse response = new OrderResponse();
        response.setAmount(result.getAmount().getPayerTotal());
        response.setAttach(result.getAttach());
        response.setSuccessTime(LocalDateTime.parse(result.getSuccessTime(), DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        response.setTradeType(TradeType.forType(result.getTradeType()));
        response.setTradeState(TradeState.forState(result.getTradeState()));
        response.setPayId(result.getPayer().getOpenid());
        response.setTransactionId(result.getTransactionId());
        return response;
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
