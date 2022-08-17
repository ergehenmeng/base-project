package com.eghm.service.business.impl;

import com.eghm.dao.mapper.HomestayOrderMapper;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.PayOrderService;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PrepayVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Slf4j
@Service("homestayOrderService")
@AllArgsConstructor
public class HomestayOrderServiceImpl implements HomestayOrderService, PayOrderService {

    private final HomestayOrderMapper homestayOrderMapper;

    @Override
    public PrepayVO prepay(Long orderId, String buyerId, TradeType tradeType) {
        return null;
    }

    @Override
    public void orderExpire(String orderNo) {

    }

    @Override
    public void orderCancel(Long orderId) {

    }

    @Override
    public void orderPaying(Long orderId, Long userId) {

    }

    @Override
    public void orderDelete(Long orderId, Long userId) {

    }

    @Override
    public void orderPay(String orderNo) {

    }

    @Override
    public void orderRefund(String outTradeNo, String outRefundNo) {

    }

    @Override
    public List<String> getPayingList() {
        return null;
    }
}
