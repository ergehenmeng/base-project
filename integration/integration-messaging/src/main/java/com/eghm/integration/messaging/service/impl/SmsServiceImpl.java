package com.eghm.integration.messaging.service.impl;

import com.eghm.platform.config.service.SysConfigApi;

import cn.hutool.core.util.IdUtil;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.integration.messaging.service.SendSmsService;
import com.eghm.integration.messaging.service.SmsService;
import com.eghm.foundation.core.constants.CacheConstant;
import com.eghm.foundation.core.constants.ConfigConstant;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.enums.TemplateType;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.integration.messaging.entity.SmsLog;
import com.eghm.integration.messaging.service.SmsLogService;
import com.eghm.foundation.web.utility.CacheUtil;
import com.eghm.foundation.core.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.eghm.foundation.core.constants.CommonConstant.MAX_ERROR_NUM;
import static com.eghm.foundation.core.constants.CommonConstant.SMS_CODE_EXPIRE;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:46
 */
@Slf4j
@Service
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final SmsLogService smsLogService;

    private final SendSmsService sendSmsService;

    @Override
    @Async
    public void sendSmsCode(TemplateType templateType, String mobile, String ip) {
        this.smsIpLimitCheck(ip);
        this.sendSmsCode(templateType, mobile);
    }

    @Override
    public String verifySmsCode(TemplateType templateType, String mobile, String smsCode) {
        String originalSmsCode = cacheService.getValue(String.format(CacheConstant.SMS_PREFIX, templateType.getValue(), mobile));
        if (originalSmsCode == null) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        String key = templateType.getValue() + mobile;
        Integer present = CacheUtil.SMS_VERIFY_CACHE.getIfPresent(key);
        if (present != null && present > MAX_ERROR_NUM) {
            this.cleanSmsCode(templateType, mobile);
            throw new BusinessException(ErrorCode.SMS_CODE_VERIFY_ERROR);
        }
        if (!originalSmsCode.equalsIgnoreCase(smsCode)) {
            CacheUtil.SMS_VERIFY_CACHE.asMap().merge(key,  1, Integer::sum);
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_ERROR);
        }
        this.cleanSmsCode(templateType, mobile);
        CacheUtil.SMS_VERIFY_CACHE.invalidate(key);
        String uuid = IdUtil.fastSimpleUUID();
        cacheService.setValue(CacheConstant.VERIFY_PREFIX + uuid, true, SMS_CODE_EXPIRE);
        cacheService.setValue(CacheConstant.VERIFY_MOBILE_PREFIX + uuid, mobile, SMS_CODE_EXPIRE);
        return uuid;
    }

    @Override
    public boolean verifyRequestId(String requestId) {
        String key = CacheConstant.VERIFY_PREFIX + requestId;
        boolean exist = cacheService.exist(key);
        if (exist) {
            cacheService.delete(key);
        }
        return exist;
    }

    @Override
    @Async
    public void sendSms(String mobile, TemplateType templateType, String... params) {
        this.doSendSms(mobile, templateType, params);
    }

    /**
     *
     * 1. 校验ip地址短信发送次数上限
     * 2. 校验手机号发送次数上限
     * 3. 发送短信验证码, 并记录短信日志
     * 4. 将短信验证码存入缓存, 并设置过期时间
     *
     * @param templateType 短信验证码类型
     * @param mobile       手机号
     */
    private void sendSmsCode(TemplateType templateType, String mobile) {
        this.smsLimitCheck(templateType.getValue(), mobile);
        String smsCode = StringUtil.randomNumber();
        this.doSendSms(mobile, templateType, smsCode);
        this.saveSmsCode(templateType.getValue(), mobile, smsCode);
        long expire = sysConfigApi.getLong(ConfigConstant.SMS_TYPE_INTERVAL);
        cacheService.setValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, templateType.getValue(), mobile), true, expire);
    }
    
    /**
     * 发送短信, 并记录短信日志
     *
     * @param mobile 手机号
     * @param templateType 短信模板类型
     * @param params 短信模板参数
     */
    public void doSendSms(String mobile, TemplateType templateType, String... params) {
        int state = sendSmsService.sendSms(mobile, templateType, params);
        SmsLog smsLog = SmsLog.builder().content(StringUtil.parse(templateType.getContent(), params)).mobile(mobile).templateType(templateType).state(state).build();
        smsLogService.addSmsLog(smsLog);
    }

    /**
     * 删除短信验证码
     *
     * @param templateType 短信类型
     * @param mobile       手机号码
     */
    private void cleanSmsCode(TemplateType templateType, String mobile) {
        cacheService.delete(String.format(CacheConstant.SMS_PREFIX, templateType.getValue(), mobile));
    }

    /**
     * 保存发送的短信
     *
     * @param smsType 短信类型
     * @param mobile  手机号码
     * @param smsCode 短信验证码
     */
    private void saveSmsCode(String smsType, String mobile, String smsCode) {
        cacheService.setValue(String.format(CacheConstant.SMS_PREFIX, smsType, mobile), smsCode, sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600));
    }

    /**
     * 根据短信类型和手机号判断短信发送间隔及短信次数是否上限
     *
     * @param templateType 短信类型
     * @param mobile       手机号
     */
    private void smsLimitCheck(String templateType, String mobile) {
        // 短信时间间隔判断
        String value = cacheService.getValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, templateType, mobile));
        if (value != null) {
            throw new BusinessException(ErrorCode.SMS_FREQUENCY_FAST);
        }
        // 单位小时统一类型内短信限制
        boolean limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_HOUR_LIMIT, templateType, mobile), sysConfigApi.getInt(ConfigConstant.SMS_TYPE_HOUR_LIMIT), 3600);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_HOUR_LIMIT);
        }
        // 当天同一类型短信限制
        limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_DAY_LIMIT, templateType, mobile), sysConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY_LIMIT), 86400);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_DAY_LIMIT);
        }
        // 当天手机号限制
        limit = cacheService.limit(String.format(CacheConstant.SMS_DAY, mobile), sysConfigApi.getInt(ConfigConstant.SMS_DAY_LIMIT), 86400);
        if (limit) {
            throw new BusinessException(ErrorCode.MOBILE_DAY_LIMIT);
        }
    }

    /**
     * 校验ip地址短信是否上限
     *
     * @param ip ip地址
     */
    private void smsIpLimitCheck(String ip) {
        int ipLimit = sysConfigApi.getInt(ConfigConstant.SMS_IP_LIMIT);
        // 短信时间间隔判断
        boolean limit = cacheService.limit(CacheConstant.SMS_IP_LIMIT + ip, ipLimit, 86400);
        if (limit) {
            log.info("ip限制短信发送量已达上限 [{}]", ip);
            throw new BusinessException(ErrorCode.MOBILE_DAY_LIMIT);
        }
    }

}
