package com.eghm.service.system.impl;

import com.eghm.dao.mapper.system.SmsLogMapper;
import com.eghm.dao.model.business.SmsLog;
import com.eghm.model.dto.business.sms.SmsLogQueryRequest;
import com.eghm.service.system.SmsLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:47
 */
@Service("smsLogService")
@Transactional(rollbackFor = RuntimeException.class)
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Override
    @Async
    public void addSmsLog(SmsLog smsLog) {
        smsLogMapper.insertSelective(smsLog);
    }

    @Override
    public int countSms(String smsType, String mobile, Date startTime, Date endTime) {
        return smsLogMapper.countSms(smsType, mobile, startTime, endTime);
    }

    @Override
    public PageInfo<SmsLog> getByPage(SmsLogQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<SmsLog> list = smsLogMapper.getList(request);
        return new PageInfo<>(list);
    }

}
