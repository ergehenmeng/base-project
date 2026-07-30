package com.eghm.integration.payment.service.impl;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.eghm.platform.config.service.SysConfigApi;
import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.integration.payment.dto.PrepayDTO;
import com.eghm.integration.payment.enums.PayChannel;
import com.eghm.integration.payment.enums.TradeType;
import com.eghm.integration.payment.service.CreatePayService;
import com.eghm.integration.payment.vo.PrepayVO;
import com.eghm.foundation.core.utils.DateUtil;
import com.eghm.foundation.core.utils.DecimalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2025/8/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliJsApiCreatePayServiceImpl implements CreatePayService {

    private final SysConfigApi sysConfigApi;

    private DefaultAlipayClient defaultAlipayClient;
    
    private final ApplicationProperties applicationProperties;

    @Autowired(required = false)
    public void setDefaultAlipayClient(DefaultAlipayClient defaultAlipayClient) {
        this.defaultAlipayClient = defaultAlipayClient;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return TradeType.JSAPI_PAY == tradeType;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        model.setOutTradeNo(dto.getTradeNo());
        model.setTotalAmount(DecimalUtil.centToYuan(dto.getAmount()));
        model.setSubject(dto.getDescription());
        model.setProductCode(TradeType.JSAPI_PAY.getCode());
        model.setOpAppId(applicationProperties.getPay().getAli().getAppId());
        model.setBuyerOpenId(dto.getBuyerId());
        int expireTime = sysConfigApi.getInt(ConfigConstant.ORDER_EXPIRE_TIME);
        model.setTimeExpire(DateUtil.format(LocalDateTime.now().plusSeconds(expireTime)));
        request.setBizModel(model);
        request.setNotifyUrl(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_PAY_NOTIFY_URL);
        AlipayTradeCreateResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝JSAPI支付创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (!response.isSuccess()) {
            log.error("支付宝JSAPI支付下单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setOutTradeNo(response.getTradeNo());
        vo.setTradeNo(response.getOutTradeNo());
        vo.setPayChannel(PayChannel.ALIPAY);
        return vo;
    }
}
