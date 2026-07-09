package com.eghm.application.payment.service.impl;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.domain.payment.enums.TradeType;
import com.eghm.application.payment.port.in.AggregatePayService;
import com.eghm.application.payment.port.in.PayRequestLogService;
import com.eghm.application.payment.port.out.PayService;
import com.eghm.application.payment.vo.PayOrderVO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/23
 */
@Slf4j
@AllArgsConstructor
@Service("aggregatePayService")
public class AggregatePayServiceImpl implements AggregatePayService {

    private final List<PayService> serviceList;

    private final PayRequestLogService payRequestLogService;

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        PrepayVO vo = null;
        try {
            vo = this.getPayService(dto.getTradeType()).createPrepay(dto);
        } finally {
            payRequestLogService.insertPayLog(dto, vo);
        }
        return vo;
    }

    @Override
    public PayOrderVO queryOrder(TradeType tradeType, String tradeNo) {
        return this.getPayService(tradeType).queryOrder(tradeNo);
    }

    @Override
    public void closeOrder(TradeType tradeType, String tradeNo) {
        this.getPayService(tradeType).closeOrder(tradeNo);
    }

    @Override
    public void applyRefund(RefundDTO dto) {
        RefundVO vo = null;
        try {
            vo = this.getPayService(dto.getTradeType()).applyRefund(dto);
        } finally {
            payRequestLogService.insertRefundLog(dto, vo);
        }
    }

    @Override
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
