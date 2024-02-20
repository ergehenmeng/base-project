package com.eghm.service.business.impl;

import com.eghm.mapper.ScanRechargeLogMapper;
import com.eghm.model.ScanRechargeLog;
import com.eghm.service.business.ScanRechargeLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 扫码支付记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-20
 */
@Slf4j
@AllArgsConstructor
@Service("scanRechargeLogService")
public class ScanRechargeLogServiceImpl implements ScanRechargeLogService {

    private final ScanRechargeLogMapper scanRechargeLogMapper;

    @Override
    public void insert(ScanRechargeLog scanRechargeLog) {
        scanRechargeLogMapper.insert(scanRechargeLog);
    }
}
