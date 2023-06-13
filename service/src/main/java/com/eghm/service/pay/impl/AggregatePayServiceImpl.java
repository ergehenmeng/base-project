package com.eghm.service.pay.impl;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.service.business.PayRequestLogService;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.OrderVO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/7/23
 */
@Service("aggregatePayService")
@AllArgsConstructor
@Slf4j
public class AggregatePayServiceImpl implements AggregatePayService {

    private final List<PayService> serviceList;

    private final PayRequestLogService payRequestLogService;

    @Override
    public PrepayVO createPrepay(PrepayDTO dto) {
        PrepayVO vo = null;
        try {
            vo = getPayService(dto.getTradeType()).createPrepay(dto);
        } finally {
            payRequestLogService.insertPayLog(dto, vo);
        }
        return vo;
    }

    @Override
    public OrderVO queryOrder(TradeType tradeType, String outTradeNo) {
        return getPayService(tradeType).queryOrder(outTradeNo);
    }

    @Override
    public void closeOrder(TradeType tradeType, String outTradeNo) {
        getPayService(tradeType).closeOrder(outTradeNo);
    }

    @Override
    public RefundVO applyRefund(RefundDTO dto) {
        RefundVO vo = null;
        try {
            vo = getPayService(dto.getTradeType()).applyRefund(dto);
        } finally {
            payRequestLogService.insertRefundLog(dto, vo);
        }
        return vo;
    }

    @Override
    public RefundVO queryRefund(TradeType tradeType, String outTradeNo, String outRefundNo) {
        return getPayService(tradeType).queryRefund(outTradeNo, outRefundNo);
    }

    /**
     * 查询可适配的交易方式
     * @param tradeType 交易类型
     * @return 支付方式
     */
    private PayService getPayService(TradeType tradeType) {
        for (PayService service : serviceList) {
            if (service.supported(tradeType)) {
                return service;
            }
        }
        log.error("不支持该支付方式 [{}]", tradeType);
        throw new BusinessException(ErrorCode.UNKNOWN_PAY_TYPE);
    }
}
