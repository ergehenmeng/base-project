package com.eghm.service.business;

import com.eghm.model.ScanRechargeLog;

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
     * 新增扫码充值记录
     *
     * @param scanRechargeLog 扫码充值
     */
    void insert(ScanRechargeLog scanRechargeLog);
}
