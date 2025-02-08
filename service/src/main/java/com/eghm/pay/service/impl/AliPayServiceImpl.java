package com.eghm.pay.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.AliPayException;
import com.eghm.exception.BusinessException;
import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.dto.RefundDTO;
import com.eghm.pay.enums.*;
import com.eghm.pay.service.PayService;
import com.eghm.pay.vo.PayOrderVO;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.pay.vo.RefundVO;
import com.eghm.utils.DateUtil;
import com.eghm.utils.DecimalUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.eghm.utils.StringUtil.isBlank;
import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2022/7/24
 */
@Service("aliPayService")
@AllArgsConstructor
@Slf4j
public class AliPayServiceImpl implements PayService {

    /**
     * 退款成功
     */
    private static final String REFUND_SUCCESS = "REFUND_SUCCESS";

    private final SysConfigApi sysConfigApi;

    @Override
    public boolean supported(TradeType tradeType) {
        return tradeType.getPayChannel() == PayChannel.ALIPAY;
    }

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        if (dto.getTradeType() == TradeType.ALI_PAY) {
            return this.createCommonPrepay(dto);
        }
        return this.createPcPrepay(dto);
    }

    @Override
    public PayOrderVO queryOrder(String tradeNo) {
        AlipayTradeQueryResponse response;
        try {
            response = Factory.Payment.Common().query(tradeNo);
        } catch (Exception e) {
            log.error("支付宝查询支付订单失败 [{}]", tradeNo, e);
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        if (isNotBlank(response.getSubCode())) {
            log.error("支付宝支付订单查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.ORDER_QUERY_ERROR);
        }
        PayOrderVO vo = new PayOrderVO();
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
    public void closeOrder(String tradeNo) {
        try {
            Factory.Payment.Common().close(tradeNo);
        } catch (Exception e) {
            log.error("支付宝关闭支付订单失败 [{}]", tradeNo, e);
        }
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        AlipayTradeRefundResponse response;
        try {
            response = Factory.Payment.Common()
                    .asyncNotify(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_REFUND_NOTIFY_URL)
                    .optional("out_request_no", dto.getRefundNo())
                    .optional("refund_reason", dto.getReason())
                    .refund(dto.getTradeNo(), DecimalUtil.centToYuan(dto.getAmount()));
        } catch (Exception e) {
            log.error("支付宝退款申请发起失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.ALI_REFUND_APPLY);
        }
        if (isNotBlank(response.getSubCode())) {
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
        AlipayTradeFastpayRefundQueryResponse response;
        try {
            response = Factory.Payment.Common().optional("query_options", Lists.newArrayList("gmt_refund_pay", "refund_detail_item_list")).queryRefund(tradeNo, refundNo);
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}] [{}]", tradeNo, refundNo, e);
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        if (isNotBlank(response.getSubCode())) {
            log.error("支付宝退款状态查询响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.REFUND_QUERY);
        }
        RefundVO vo = new RefundVO();
        vo.setSuccessTime(DateUtil.parseLocalDateTime(response.getGmtRefundPay()));
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
    public WxPayNotifyV3Result parsePayNotify(String notifyData, SignatureHeader header) {
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
            flag = Factory.Payment.Common().verifyNotify(param);
        } catch (Exception e) {
            log.error("支付宝退款状态查询失败 [{}]", param, e);
        }
        if (!flag) {
            throw new AliPayException(ErrorCode.NOTIFY_SIGN_ERROR);
        }
    }

    /**
     * 普通支付宝支付
     *
     * @param dto 下单参数
     * @return 预支付参数
     */
    private PrepayVO createCommonPrepay(PrepayDTO dto) {
        AlipayTradeCreateResponse response;
        try {
            response = Factory.Payment.Common().optional("passback_params", dto.getAttach()).asyncNotify(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_PAY_NOTIFY_URL)
                    .create(dto.getDescription(), dto.getTradeNo(), DecimalUtil.centToYuan(dto.getAmount()), dto.getBuyerId());
        } catch (Exception e) {
            log.error("支付宝创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (isNotBlank(response.getSubCode())) {
            log.error("支付宝下单响应信息异常 [{}] [{}] [{}]", response.getSubCode(), response.getMsg(), response.getSubMsg());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setOutTradeNo(response.tradeNo);
        vo.setPayChannel(PayChannel.ALIPAY);
        return vo;
    }

    /**
     * pc扫码支付创建预支付订单
     *
     * @param dto 订单信息
     * @return 支付信息
     */
    private PrepayVO createPcPrepay(PrepayDTO dto) {
        AlipayTradePagePayResponse response;
        try {
            response = Factory.Payment.Page().optional("passback_params", dto.getAttach()).asyncNotify(sysConfigApi.getString(ConfigConstant.PAY_NOTIFY_HOST) + CommonConstant.ALI_PAY_NOTIFY_URL)
                    .pay(dto.getDescription(), dto.getTradeNo(), DecimalUtil.centToYuan(dto.getAmount()), null);
        } catch (Exception e) {
            log.error("支付宝扫码付创建支付订单失败 [{}]", dto, e);
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        if (isBlank(response.getBody())) {
            log.error("支付宝扫码付下单响应信息异常 [{}] ", response.toMap());
            throw new BusinessException(ErrorCode.PAY_ORDER_ERROR);
        }
        PrepayVO vo = new PrepayVO();
        vo.setQrCodeHtml(response.getBody());
        vo.setPayChannel(PayChannel.ALIPAY);
        return vo;
    }
}
