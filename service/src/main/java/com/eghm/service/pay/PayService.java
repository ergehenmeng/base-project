package com.eghm.service.pay;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.enums.MerchantType;
import com.eghm.service.pay.response.OrderResponse;
import com.eghm.service.pay.response.PrepayResponse;

/**
 * @author 二哥很猛
 */
public interface PayService {

    /**
     * 生成预支付订单信息 不支持优惠券等信息
     * @param dto 预支付信息
     * @return prepay_id
     */
    PrepayResponse createPrepay(PrepayDTO dto);

    /**
     * 查询订单信息
     * @param orderNo 订单号
     * @param merchantType 商户信息
     * @return 订单信息
     */
    OrderResponse queryOrder(String orderNo, MerchantType merchantType);
}