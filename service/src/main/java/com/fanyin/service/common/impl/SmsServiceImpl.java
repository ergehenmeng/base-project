package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.DateUtil;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.SendSmsService;
import com.fanyin.service.common.SmsService;
import com.fanyin.service.system.SmsLogService;
import com.fanyin.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private SmsLogService smsLogService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Override
    public void sendSmsCode(String smsType, String mobile) {
        Object value = cacheService.getValue(CacheConstant.SMS_TYPE_INTERVAL + smsType + mobile);
        if(value == null){
            throw new BusinessException(ErrorCodeEnum.SMS_FREQUENCY_FAST);
        }
        int smsTypeHour = systemConfigApi.getInt(CacheConstant.SMS_TYPE_HOUR);
        Date now = DateUtil.getNow();
        Date hourStart = DateUtil.addHours(now,-1);
        //TODO 可以放缓存中
        int countSms = smsLogService.countSms(smsType, mobile, hourStart,now);
        if(countSms > smsTypeHour){
            throw new BusinessException(ErrorCodeEnum.SMS_HOUR_LIMIT);
        }
        Date dayStart = DateUtil.beginOfDay(now);
        countSms = smsLogService.countSms(smsType, mobile, dayStart, now);
        int smsTypeDay = systemConfigApi.getInt(CacheConstant.SMS_TYPE_DAY);
        if(countSms > smsTypeDay){
            throw new BusinessException(ErrorCodeEnum.SMS_DAY_LIMIT);
        }
        int smsDay = systemConfigApi.getInt(CacheConstant.SMS_DAY);
        countSms = smsLogService.countSms(null, mobile, dayStart, now);
        if(countSms > smsDay){
            throw new BusinessException(ErrorCodeEnum.SMS_MOBILE_DAY_LIMIT);
        }
        String smsCode = StringUtil.randomNumber();
        sendSmsService.sendSms(mobile,smsCode);
        int anInt = systemConfigApi.getInt(ConfigConstant.SMS_TYPE_INTERVAL);
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
