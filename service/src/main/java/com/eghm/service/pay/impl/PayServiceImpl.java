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
import com.eghm.service.pay.request.BaseRequest;
import com.eghm.service.pay.request.PrepayRequest;
import com.eghm.service.pay.response.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author 二哥很猛
 */
@Service("payService")
@Slf4j
public class PayServiceImpl implements PayService {

    private AppletPayConfigService appletPayConfigService;

    private NumberService numberService;

    private JsonService jsonService;

    @Autowired
    public void setAppletPayConfigService(AppletPayConfigService appletPayConfigService) {
        this.appletPayConfigService = appletPayConfigService;
    }

    @Autowired
    public void setNumberService(NumberService numberService) {
        this.numberService = numberService;
    }

    @Autowired
    public void setJsonService(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    @Override
    public PrepayResponse createPrepay(PrepayDTO dto) {
        AppletPayConfig config = appletPayConfigService.getByNid(dto.getMerchantType());
        if (config == null) {
            log.error("未配置小程序商户信息 [{}]", dto.getMerchantType().getCode());
            throw new BusinessException(ErrorCode.PAY_CONFIG_ERROR);
        }
        PrepayRequest request = this.createPrepayRequest(dto, config);
        PrepayResponse response = this.doPost(request, PayConstant.PREPAY_URL, PrepayResponse.class);
        response.setOrderNo(request.getOrderNo());
        return response;
    }

    @Override
    public void queryOrder(String orderNo) {

    }


    private <T> T sendGet(String url, Class<T> cls) {

        return null;
    }


    private <T> T doPost(BaseRequest dto, String url, Class<T> cls) {
        String request = jsonService.toJson(dto);
        log.info("post请求参数 [{}] [{}]", url, dto);
        try {
            HttpResponse response = HttpUtil.createPost(url).body(request).execute();
            String body = response.body();
            log.info("post响应数据 [{}]", body);
            return jsonService.fromJson(body, cls);
        } catch (RuntimeException e) {
            log.error("post请求异常", e);
            throw new BusinessException(ErrorCode.CREATE_PAY_ERROR);
        }
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




}
