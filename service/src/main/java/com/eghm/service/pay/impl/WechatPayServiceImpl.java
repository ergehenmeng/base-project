package com.eghm.service.pay.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.AppletPayConfig;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.NumberService;
import com.eghm.service.pay.AppletPayConfigService;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.constant.PayConstant;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.MerchantType;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.request.BaseRequest;
import com.eghm.service.pay.request.PrepayRequest;
import com.eghm.service.pay.response.ErrorResponse;
import com.eghm.service.pay.response.OrderResponse;
import com.eghm.service.pay.response.PrepayResponse;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.service.WxPayService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private final AppletPayConfigService appletPayConfigService;

    private final NumberService numberService;

    private final JsonService jsonService;

    private final WxPayService wxPayService;

    @Override
    public boolean supported(TradeType tradeType) {
        return transferType(tradeType) != null;
    }

    @Override
    public PrepayResponse createPrepay(PrepayDTO dto) {

        // TODO 待完成

        AppletPayConfig config = this.getConfig(dto.getMerchantType());
        PrepayRequest request = this.createPrepayRequest(dto, config);
        PrepayResponse response = this.doPost(request, PayConstant.PREPAY_URL, PrepayResponse.class);
        response.setOrderNo(request.getOrderNo());
        return response;
    }

    @Override
    public OrderResponse queryOrder(String orderNo, MerchantType merchantType) {
        AppletPayConfig config = this.getConfig(merchantType);
        return this.doGet(String.format(PayConstant.QUERY_ORDER_URL, orderNo, config.getMerchantId()), OrderResponse.class);
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


    private <T> T doGet(String url, Class<T> cls) {
        log.info("get请求参数 [{}]", url);
        String response = HttpUtil.get(url);
        return this.parseResponse(response, cls);
    }


    private <T> T doPost(BaseRequest dto, String url, Class<T> cls) {
        String request = jsonService.toJson(dto);
        log.info("post请求参数 [{}] [{}]", url, dto);
        HttpResponse response = HttpUtil.createPost(url).body(request).execute();
        return this.parseResponse(response.body(), cls);
    }

    /**
     * 创建预支付请求信息
     * @param dto 支付信息
     * @param config 支付配置信息
     * @return request
     */
    private PrepayRequest createPrepayRequest(PrepayDTO dto, AppletPayConfig config) {
        PrepayRequest request = new PrepayRequest();
        request.setAppId(config.getAppId());
        request.setMerchantId(config.getMerchantId());
        request.setAmount(dto.getAmount());
        request.setOpenId(dto.getOpenId());
        request.setAttach(dto.getAttach());
        request.setDescription(dto.getDescription());
        request.setNotifyUrl(config.getNotifyUrl());
        request.setOrderNo(numberService.getNumber(config.getOrderPrefix()));
        request.setExpireTime(LocalDateTime.now().plusSeconds(config.getExpireTime()).withNano(0).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        return request;
    }


    private <T> T parseResponse(String response, Class<T> cls) {
        ErrorResponse errorResponse = jsonService.fromJson(response, ErrorResponse.class);
        if (errorResponse.getCode() != null) {
            log.error("接口响应状态失败 [{}]", response);
            throw new BusinessException(ErrorCode.PAY_ERROR.getCode(), errorResponse.getMessage());
        }
        return jsonService.fromJson(response, cls);
    }

    private AppletPayConfig getConfig(MerchantType merchantType) {
        AppletPayConfig config = appletPayConfigService.getByNid(merchantType);
        if (config == null) {
            log.error("未配置小程序商户信息 [{}]", merchantType.getCode());
            throw new BusinessException(ErrorCode.PAY_CONFIG_ERROR);
        }
        return config;
    }


}
