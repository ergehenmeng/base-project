package com.eghm.infrastructure.payment.service.impl;

import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.domain.payment.enums.PayChannel;
import com.eghm.domain.payment.enums.TradeType;
import com.eghm.application.payment.service.CreatePayService;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.shared.utils.DateUtil;
import com.eghm.application.shared.utils.DecimalUtil;
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
public class AliAppCreatePayServiceImpl implements CreatePayService {

    private final SysConfigService sysConfigService;

    private DefaultAlipayClient defaultAlipayClient;

    @Autowired(required = false)
    public void setDefaultAlipayClient(DefaultAlipayClient defaultAlipayClient) {
        this.defaultAlipayClient = defaultAlipayClient;
    }

    @Override
    public boolean supported(TradeType tradeType) {
        return TradeType.QUICK_MSECURITY_PAY == tradeType;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(dto.getTradeNo());
        model.setTotalAmount(DecimalUtil.centToYuan(dto.getAmount()));
        model.setSubject(dto.getDescription());
        model.setProductCode(TradeType.QUICK_MSECURITY_PAY.getCode());
        int expireTime = sysConfigService.getInt(ConfigConstant.ORDER_EXPIRE_TIME);
        model.setTimeExpire(DateUtil.format(LocalDateTime.now().plusSeconds(expireTime)));
        request.setBizModel(model);
        request.setNotifyUrl(sysConfigService.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_PAY_NOTIFY_URL);
        AlipayTradeAppPayResponse response;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (Exception e) {
            log.error("支付宝App支付创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (!response.isSuccess()) {
            log.error("支付宝App支付下单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setTradeNo(response.getOutTradeNo());
        vo.setOutTradeNo(response.getTradeNo());
        vo.setPayChannel(PayChannel.ALIPAY);
        vo.setPackageValue(response.getOrderStr());
        return vo;
    }
}
