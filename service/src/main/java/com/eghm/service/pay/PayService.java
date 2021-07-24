package com.eghm.service.pay;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.response.PrepayResponse;

public interface PayService {

    /**
     * 生成预支付订单信息
     * @param dto 预支付信息
     * @return prepay_id
     */
    PrepayResponse createPrepay(PrepayDTO dto);

    /**
     * 查询订单信息
     * @param orderNo 订单号
     */
    void queryOrder(String orderNo);
}