package com.eghm.service.pay.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.service.pay.AggregatePayService;
import com.eghm.service.pay.PayService;
import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.response.PrepayResponse;
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

    @Override
    public PrepayResponse createPrepay(PrepayDTO dto) {
        return getPayService(dto.getTradeType()).createPrepay(dto);
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
