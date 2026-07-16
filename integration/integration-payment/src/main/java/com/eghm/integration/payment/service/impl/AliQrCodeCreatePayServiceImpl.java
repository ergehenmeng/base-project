package com.eghm.integration.payment.service.impl;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.eghm.platform.config.service.SysConfigApi;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.integration.payment.dto.PrepayDTO;
import com.eghm.integration.payment.enums.PayChannel;
import com.eghm.integration.payment.enums.TradeType;
import com.eghm.integration.payment.service.CreatePayService;
import com.eghm.integration.payment.vo.PrepayVO;
import com.eghm.foundation.core.utils.DecimalUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2025/8/18
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AliQrCodeCreatePayServiceImpl implements CreatePayService {

    private final SysConfigApi sysConfigApi;

    private DefaultAlipayClient defaultAlipayClient;

    @Autowired(required = false)
    public void setDefaultAlipayClient(DefaultAlipayClient defaultAlipayClient) {
        this.defaultAlipayClient = defaultAlipayClient;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return TradeType.QR_CODE_OFFLINE == tradeType;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(dto.getTradeNo());
        model.setTotalAmount(DecimalUtil.centToYuan(dto.getAmount()));
        model.setSubject(dto.getDescription());
        model.setProductCode(TradeType.QR_CODE_OFFLINE.getCode());
        request.setBizModel(model);
        request.setNotifyUrl(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_PAY_NOTIFY_URL);
        AlipayTradePrecreateResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝订单码创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (!response.isSuccess()) {
            log.error("支付宝订单码下单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setTradeNo(response.getOutTradeNo());
        vo.setPayChannel(PayChannel.ALIPAY);
        vo.setQrCodeUrl(response.getQrCode());
        return vo;
    }
}
