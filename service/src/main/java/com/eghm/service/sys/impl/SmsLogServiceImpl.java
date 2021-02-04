package com.eghm.service.sys.impl;

import com.eghm.dao.mapper.SmsLogMapper;
import com.eghm.dao.model.SmsLog;
import com.eghm.model.dto.sms.SmsLogQueryRequest;
import com.eghm.service.common.KeyGenerator;
import com.eghm.service.sys.SmsLogService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
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
public class SmsLogServiceImpl implements SmsLogService {

    private SmsLogMapper smsLogMapper;

    private KeyGenerator keyGenerator;

    @Autowired
    public void setSmsLogMapper(SmsLogMapper smsLogMapper) {
        this.smsLogMapper = smsLogMapper;
    }

    @Override
    @Async
    @Transactional(rollbackFor = RuntimeException.class)
    public void addSmsLog(SmsLog smsLog) {
        smsLog.setId(keyGenerator.generateKey());
        smsLogMapper.insertSelective(smsLog);
    }

    @Override
    public int countSms(String smsType, String mobile, Date startTime, Date endTime) {
        return smsLogMapper.countSms(smsType, mobile, startTime, endTime);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<SmsLog> getByPage(SmsLogQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SmsLog> list = smsLogMapper.getList(request);
        return new PageInfo<>(list);
    }

}
