package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.mapper.SmsLogMapper;
import com.eghm.model.SmsLog;
import com.eghm.dto.sms.SmsLogQueryRequest;
import com.eghm.service.sys.SmsLogService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:47
 */
@Service("smsLogService")
@AllArgsConstructor
public class SmsLogServiceImpl implements SmsLogService {

    private final SmsLogMapper smsLogMapper;

    @Override
    public Page<SmsLog> getByPage(SmsLogQueryRequest request) {
        LambdaQueryWrapper<SmsLog> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), SmsLog::getMobile, request.getQueryName());
        wrapper.eq(request.getSmsType() != null, SmsLog::getSmsType, request.getSmsType());
        wrapper.eq(request.getState() != null, SmsLog::getState, request.getState());
        wrapper.ge(request.getStartTime() != null, SmsLog::getCreateTime, request.getStartTime());
        wrapper.lt(request.getEndTime() != null, SmsLog::getCreateTime, request.getEndTime());
        return smsLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Async
    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void addSmsLog(SmsLog smsLog) {
        smsLogMapper.insert(smsLog);
    }

    @Override
    public int countSms(String smsType, String mobile, Date startTime, Date endTime) {
        return smsLogMapper.countSms(smsType, mobile, startTime, endTime);
    }

}
