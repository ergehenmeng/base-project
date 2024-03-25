package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.business.order.OfflineRefundRequest;
import com.eghm.mapper.OfflineRefundLogMapper;
import com.eghm.model.OfflineRefundLog;
import com.eghm.service.business.OfflineRefundLogService;
import com.eghm.common.JsonService;
import com.eghm.utils.DataUtil;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private final JsonService jsonService;

    @Override
    public List<Long> getRefundLog(String orderNo) {
        LambdaQueryWrapper<OfflineRefundLog> wrapper = Wrappers.lambdaQuery();
        wrapper.select(OfflineRefundLog::getNote, OfflineRefundLog::getId);
        wrapper.eq(OfflineRefundLog::getOrderNo, orderNo);
        List<OfflineRefundLog> selectList = offlineRefundLogMapper.selectList(wrapper);
        if (CollUtil.isEmpty(selectList)) {
            return Lists.newArrayList();
        }
        return selectList.stream().flatMap(refundLog -> jsonService.fromJsonList(refundLog.getNote(), Long.class).stream()).distinct().collect(Collectors.toList());
    }

    @Override
    public void insertLog(OfflineRefundRequest request) {
        OfflineRefundLog refundLog = DataUtil.copy(request, OfflineRefundLog.class);
        refundLog.setNote(jsonService.toJson(request.getVisitorList()));
        offlineRefundLogMapper.insert(refundLog);
    }

}
