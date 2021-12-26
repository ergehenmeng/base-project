package com.eghm.service.common.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.SmsType;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.SmsLog;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.SendSmsService;
import com.eghm.service.common.SmsService;
import com.eghm.service.sys.SmsLogService;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.service.sys.impl.SysConfigApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService {

    private CacheService cacheService;

    private SendSmsService sendSmsService;

    private SmsLogService smsLogService;

    private SmsTemplateService smsTemplateService;

    private SysConfigApi sysConfigApi;

    private static final String SMS_PREFIX = "sms:";

    private static final String VERIFY_PREFIX = "verify:";

    @Autowired
    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    @Autowired
    public void setSendSmsService(SendSmsService sendSmsService) {
        this.sendSmsService = sendSmsService;
    }

    @Autowired
    public void setSmsLogService(SmsLogService smsLogService) {
        this.smsLogService = smsLogService;
    }

    @Autowired
    public void setSmsTemplateService(SmsTemplateService smsTemplateService) {
        this.smsTemplateService = smsTemplateService;
    }

    @Autowired
    public void setSysConfigApi(SysConfigApi sysConfigApi) {
        this.sysConfigApi = sysConfigApi;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public void sendSmsCode(SmsType smsType, String mobile) {
        this.smsLimitCheck(smsType.getValue(), mobile);
        String template = smsTemplateService.getTemplate(smsType.getValue());
        String smsCode = StringUtil.randomNumber();
        String content = this.formatTemplate(template, smsCode);
        this.doSendSms(mobile, content, smsType);
        this.saveSmsCode(smsType.getValue(), mobile, smsCode);
        long expire = sysConfigApi.getLong(ConfigConstant.SMS_TYPE_INTERVAL);
        cacheService.setValue(CacheConstant.SMS_TYPE_INTERVAL + smsType + mobile,expire);
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
    public String getSmsCode(SmsType smsType, String mobile) {
        return cacheService.getValue(SMS_PREFIX + smsType + mobile);
    }

    @Override
    public String verifySmsCode(SmsType smsType, String mobile, String smsCode) {
        String originalSmsCode = this.getSmsCode(smsType, mobile);
        if (originalSmsCode == null) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        if (!originalSmsCode.equalsIgnoreCase(smsCode)) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_ERROR);
        }
        this.cleanSmsCode(smsType, mobile);
        String uuid = IdUtil.fastUUID();
        cacheService.setValue(VERIFY_PREFIX + uuid, true, 300000);
        return uuid;
    }

    @Override
    public boolean verifyRequestId(String requestId) {
        String key = VERIFY_PREFIX + requestId;
        boolean exist = cacheService.exist(key);
        if (exist) {
            cacheService.delete(key);
        }
        return exist;
    }

    @Override
    public void sendSms(SmsType smsType, String mobile, Object... params) {
        String template = smsTemplateService.getTemplate(smsType.getValue());
        String content = this.formatTemplate(template, params);
        this.doSendSms(mobile, content, smsType);
    }

    @Override
    public void sendSms(String mobile, String content) {
        this.doSendSms(mobile, content, SmsType.DEFAULT);
    }


    @Override
    public void sendSms(List<String> mobileList, String content) {
        mobileList.forEach(mobile -> this.sendSms(mobile, content));
    }

    /**
     * 删除短信验证码
     * @param smsType 短信类型
     * @param mobile 手机号码
     */
    private void cleanSmsCode(SmsType smsType, String mobile) {
        cacheService.getValue(SMS_PREFIX + smsType + mobile);
    }

    /**
     * 发送短信并记录短信日志
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @param smsType 短信类型
     */
    private void doSendSms(String mobile, String content, SmsType smsType) {
        byte state = sendSmsService.sendSms(mobile, content);
        SmsLog smsLog = SmsLog.builder().content(content).mobile(mobile).smsType(smsType.getValue()).state(state).build();
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
        cacheService.setValue(SMS_PREFIX + smsType + mobile, smsCode, sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600_000));
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
        if (value != null) {
            throw new BusinessException(ErrorCode.SMS_FREQUENCY_FAST);
        }
        int smsTypeHourLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_HOUR_LIMIT);
        //单位小时统一类型内短信限制
        boolean limit = cacheService.limit(CacheConstant.SMS_TYPE_HOUR_LIMIT + smsType + mobile, smsTypeHourLimit, 3600_000);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_HOUR_LIMIT);
        }
        int smsTypeDayLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY_LIMIT);
        //当天同一类型短信限制
        limit = cacheService.limit(CacheConstant.SMS_TYPE_DAY_LIMIT + smsType + mobile, smsTypeDayLimit, 86400_000);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_DAY_LIMIT);
        }
        int smsDay = sysConfigApi.getInt(ConfigConstant.SMS_DAY_LIMIT);
        //当天手机号限制
        limit = cacheService.limit(CacheConstant.SMS_DAY + mobile, smsDay, 86400_000);
        if (limit) {
            throw new BusinessException(ErrorCode.MOBILE_DAY_LIMIT);
        }
    }

}
