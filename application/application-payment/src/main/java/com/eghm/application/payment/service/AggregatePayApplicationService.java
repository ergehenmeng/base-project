package com.eghm.application.payment.service;

import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.domain.payment.enums.TradeType;
import com.eghm.application.payment.vo.PayOrderVO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 */
@Slf4j
@Service
@AllArgsConstructor
public class AggregatePayApplicationService {
    
    private final List<PayService> serviceList;
    
    private final PayRequestLogApplicationService payRequestLogService;
    
    /**
     * 生成预支付订单信息
     *
     * @param dto 预支付信息
     * @return prepay_id
     */
    public PrepayVO createPrepay(PrepayDTO dto) {
        PrepayVO vo = null;
        try {
            vo = this.getPayService(dto.getTradeType()).createPrepay(dto);
        } finally {
            payRequestLogService.insertPayLog(dto, vo);
        }
        return vo;
    }

    /**
     * 查询订单信息
     *
     * @param tradeType 交易方式
     * @param tradeNo   商户交易订单号
     * @return 订单信息
     */
    public PayOrderVO queryOrder(TradeType tradeType, String tradeNo) {
        return this.getPayService(tradeType).queryOrder(tradeNo);
    }
    
    /**
     * 关闭订单号
     *
     * @param tradeType 交易类型
     * @param tradeNo   商户订单号
     */
    public void closeOrder(TradeType tradeType, String tradeNo) {
        this.getPayService(tradeType).closeOrder(tradeNo);
    }

    /**
     * 申请退款
     *
     * @param dto 退款信息
     */
    public void applyRefund(RefundDTO dto) {
        RefundVO vo = null;
        try {
            vo = this.getPayService(dto.getTradeType()).applyRefund(dto);
        } finally {
            payRequestLogService.insertRefundLog(dto, vo);
        }
    }
    /**
     * 查询退款单号
     *
     * @param tradeType 交易类型
     * @param tradeNo   交易流水号 (支付宝必填)
     * @param refundNo  退款流水号
     * @return 退款信息
     */
    public RefundVO queryRefund(TradeType tradeType, String tradeNo, String refundNo) {
        return this.getPayService(tradeType).queryRefund(tradeNo, refundNo);
    }
    
    /**
     * 查询可适配的交易方式
     *
     * @param tradeType 交易类型
     * @return 支付方式
     */
    private PayService getPayService(TradeType tradeType) {
        for (PayService service : serviceList) {
            if (service.supported(tradeType)) {
                service.checkConfig();
                return service;
            }
        }
        log.error("不支持该支付方式 [{}]", tradeType);
        throw new BusinessException(ErrorCode.UNKNOWN_PAY_TYPE);
    }
}