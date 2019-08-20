package com.fanyin.service.common.impl;

import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.SmsService;
import com.fanyin.service.system.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SmsLogService smsLogService;

    @Override
    public void sendSms(String smsType, String mobile) {

    }

    @Override
    public String getSmsCode(String smsType, String mobile) {
        Object value = cacheService.getValue(smsType + mobile);
        if(value != null){
            return (String)value;
        }
        return null;
    }
}
