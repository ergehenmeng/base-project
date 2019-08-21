package com.fanyin.service.common.impl;

import com.fanyin.common.constant.CacheConstant;
import com.fanyin.common.enums.ErrorCodeEnum;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.constants.ConfigConstant;
import com.fanyin.service.cache.CacheService;
import com.fanyin.service.common.SendSmsService;
import com.fanyin.service.common.SmsService;
import com.fanyin.service.system.SmsLogService;
import com.fanyin.service.system.impl.SystemConfigApi;
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
    private SendSmsService sendSmsService;

    @Autowired
    private SmsLogService smsLogService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    /**
     * 模糊匹配符
     */
    private static final String MATCH = "*";

    @Override
    public void sendSmsCode(String smsType, String mobile) {
        this.smsLimitCheck(smsType, mobile);

    }

    @Override
    public String getSmsCode(String smsType, String mobile) {
        Object value = cacheService.getValue(smsType + mobile);
        if(value != null){
            return (String)value;
        }
        return null;
    }


    /**
     * 根据短信类型和手机号判断短信发送间隔及短信次数是否上限
     * @param smsType 短信类型
     * @param mobile 手机号
     */
    private void smsLimitCheck(String smsType, String mobile){
        //短信时间间隔判断
        Object value = cacheService.getValue(CacheConstant.SMS_TYPE_INTERVAL + smsType + mobile);
        if(value == null){
            throw new BusinessException(ErrorCodeEnum.SMS_FREQUENCY_FAST);
        }
        //单位小时统一类型内短信限制
        int countSms = cacheService.keySize(CacheConstant.SMS_TYPE_HOUR + smsType + mobile + MATCH);
        int smsTypeHour = systemConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY);
        if(countSms > smsTypeHour){
            throw new BusinessException(ErrorCodeEnum.SMS_HOUR_LIMIT);
        }
        //当天同一类型短信限制
        countSms = cacheService.keySize(CacheConstant.SMS_TYPE_DAY + smsType + mobile + MATCH);
        int smsTypeDay = systemConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY);
        if(countSms > smsTypeDay){
            throw new BusinessException(ErrorCodeEnum.SMS_DAY_LIMIT);
        }
        //当天手机号限制
        countSms = cacheService.keySize(CacheConstant.SMS_DAY + smsType + mobile + MATCH);
        int smsDay = systemConfigApi.getInt(ConfigConstant.SMS_DAY);
        if(countSms > smsDay){
            throw new BusinessException(ErrorCodeEnum.MOBILE_DAY_LIMIT);
        }
    }

}
