package com.eghm.pay.service;

import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.enums.TradeType;
import com.eghm.pay.vo.PrepayVO;

/**
 * @author 二哥很猛
 * @since 2025/8/18
 */
public interface CreatePayService {

    /**
     * 交易方式是否支持
     *
     * @param tradeType 交易方式
     * @return true: 支持 false:不支持
     */
    default boolean supported(TradeType tradeType) {
        return false;
    }

    /**
     * 创建预支付
     *
     * @param dto 下单必要参数
     * @return 预支付信息
     */
    PrepayVO createPrepay(PrepayDTO dto);
}
