package com.eghm.service.business;

import com.eghm.model.ScanRechargeLog;
import com.eghm.service.business.handler.context.PayNotifyContext;

/**
 * <p>
 * 扫码支付记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-20
 */
public interface ScanRechargeLogService {

    /**
     * 扫码充值异步回调
     *
     * @param context 上下文
     */
    void rechargeNotify(PayNotifyContext context);

    /**
     * 根据交易号查询充值记录
     *
     * @param tradeNo 交易单号
     * @return 扫码充值记录
     */
    ScanRechargeLog getByTradeNo(String tradeNo);

}
