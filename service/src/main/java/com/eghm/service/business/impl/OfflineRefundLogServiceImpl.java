package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.mapper.OfflineRefundLogMapper;
import com.eghm.model.OfflineRefundLog;
import com.eghm.service.business.OfflineRefundLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 线下退款记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-14
 */
@Service("offlineRefundLogService")
@AllArgsConstructor
public class OfflineRefundLogServiceImpl implements OfflineRefundLogService {

    private final OfflineRefundLogMapper offlineRefundLogMapper;

    @Override
    public List<Long> getTicketRefundLog(String orderNo) {
        LambdaQueryWrapper<OfflineRefundLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OfflineRefundLog::getOrderNo, orderNo);

        return null;
    }
}
