package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.constant.SmsTypeConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.business.SmsLog;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.SendSmsService;
import com.eghm.service.common.SmsService;
import com.eghm.service.system.SmsLogService;
import com.eghm.service.system.SmsTemplateService;
import com.eghm.service.system.impl.SystemConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
@Service("smsService")
@Transactional(rollbackFor = RuntimeException.class)
public class SmsServiceImpl implements SmsService {

    @Autowired
    private CacheService cacheService;

    @Autowired
    private SendSmsService sendSmsService;

    @Autowired
    private SmsLogService smsLogService;

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    /**
     * 模糊匹配符
     */
    private static final String MATCH = "*";

    /**
     * 验证码过期时间 10分钟
     */
    private static final int SMS_CODE_EXPIRE = 600;

    @Override
    public void sendSmsCode(String smsType, String mobile) {
        this.smsLimitCheck(smsType, mobile);
        String template = smsTemplateService.getTemplate(smsType);
        String smsCode = StringUtil.randomNumber();
        String content = this.formatTemplate(template, smsCode);
        this.doSendSms(mobile, content, smsType);
        this.saveSmsCode(smsType, mobile, smsCode);
        this.smsLimitSet(smsType, mobile, smsCode);
    }


    /**
     * 根据模板和参数填充数据
     *
     * @param template 模板
     * @param params   参数
     * @return 填充后的数据
     */
    private String formatTemplate(String template, Object... params) {
        return MessageFormat.format(template, params);
    }

    @Override
    public String getSmsCode(String smsType, String mobile) {
        return cacheService.getValue(smsType + mobile);
    }

    @Override
    public void verifySmsCode(String smsType, String mobile, String smsCode) {
        String originalSmsCode = this.getSmsCode(smsType, mobile);
        if (originalSmsCode == null) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        if (!originalSmsCode.equalsIgnoreCase(smsCode)) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_ERROR);
        }
    }

    @Override
    public void sendSms(String smsType, String mobile, Object... params) {
        String template = smsTemplateService.getTemplate(smsType);
        String content = this.formatTemplate(template, params);
        this.doSendSms(mobile, content, smsType);
    }

    @Override
    public void sendSms(String mobile, String content) {
        this.doSendSms(mobile, content, SmsTypeConstant.DEFAULT);
    }


    @Override
    public void sendSms(List<String> mobileList, String content) {
        mobileList.forEach(mobile -> this.sendSms(mobile, content));
    }

    /**
     * 发送短信并记录短信日志
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @param smsType 短信类型
     */
    private void doSendSms(String mobile, String content, String smsType) {
        byte state = sendSmsService.sendSms(mobile, content);
        SmsLog smsLog = SmsLog.builder().content(content).mobile(mobile).smsType(smsType).state(state).build();
        smsLogService.addSmsLog(smsLog);
    }

    /**
     * 保存发送的短信
     *
     * @param smsType 短信类型
     * @param mobile  手机号码
     * @param smsCode 短信验证码
     */
    private void saveSmsCode(String smsType, String mobile, String smsCode) {
        cacheService.setValue(smsType + mobile, smsCode, SMS_CODE_EXPIRE);
    }

    /**
     * 保存短信信息,用于发送短信前的校验
     *
     * @param smsType 短信类型
     * @param mobile  手机号
     */
    private void smsLimitSet(String smsType, String mobile, String smsCode) {
        cacheService.setValue(CacheConstant.SMS_TYPE_INTERVAL + smsType + mobile, 1, systemConfigApi.getInt(ConfigConstant.SMS_TYPE_INTERVAL));
        cacheService.setValue(CacheConstant.SMS_TYPE_HOUR + smsType + mobile + smsCode, 1, 3600);
        Date expireDate = DateUtil.endOfDay(DateUtil.getNow());
        cacheService.setValue(CacheConstant.SMS_TYPE_DAY + smsType + mobile + smsCode, 1, expireDate);
        cacheService.setValue(CacheConstant.SMS_DAY + mobile + smsCode, 1, expireDate);
    }

    /**
     * 根据短信类型和手机号判断短信发送间隔及短信次数是否上限
     *
     * @param smsType 短信类型
     * @param mobile  手机号
     */
    private void smsLimitCheck(String smsType, String mobile) {
        //短信时间间隔判断
        String value = cacheService.getValue(CacheConstant.SMS_TYPE_INTERVAL + smsType + mobile);
        if (value == null) {
            throw new BusinessException(ErrorCode.SMS_FREQUENCY_FAST);
        }
        //单位小时统一类型内短信限制
        int countSms = cacheService.keySize(CacheConstant.SMS_TYPE_HOUR + smsType + mobile + MATCH);
        int smsTypeHour = systemConfigApi.getInt(ConfigConstant.SMS_TYPE_HOUR);
        if (countSms > smsTypeHour) {
            throw new BusinessException(ErrorCode.SMS_HOUR_LIMIT);
        }
        //当天同一类型短信限制
        countSms = cacheService.keySize(CacheConstant.SMS_TYPE_DAY + smsType + mobile + MATCH);
        int smsTypeDay = systemConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY);
        if (countSms > smsTypeDay) {
            throw new BusinessException(ErrorCode.SMS_DAY_LIMIT);
        }
        //当天手机号限制
        countSms = cacheService.keySize(CacheConstant.SMS_DAY + mobile + MATCH);
        int smsDay = systemConfigApi.getInt(ConfigConstant.SMS_DAY);
        if (countSms > smsDay) {
            throw new BusinessException(ErrorCode.MOBILE_DAY_LIMIT);
        }
    }

}
