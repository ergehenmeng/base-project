package com.fanyin.service.system.impl;

import com.fanyin.dao.mapper.system.SmsLogMapper;
import com.fanyin.dao.model.system.SmsLog;
import com.fanyin.service.system.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:47
 */
@Service("smsLogService")
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Override
    public void addSmsLog(SmsLog smsLog) {
        smsLogMapper.insertSelective(smsLog);
    }

    @Override
    public int countSms(String smsType, String mobile, Date startTime, Date endTime) {
        return 0;
    }
}
