package com.eghm.common.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.SendSmsService;
import com.eghm.common.SmsService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SmsTemplateType;
import com.eghm.exception.BusinessException;
import com.eghm.model.SmsLog;
import com.eghm.service.sys.SmsLogService;
import com.eghm.utils.CacheUtil;
import com.eghm.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.eghm.constants.CommonConstant.MAX_ERROR_NUM;
import static com.eghm.constants.CommonConstant.SMS_CODE_EXPIRE;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:46
 */
@Slf4j
@Service("smsService")
@AllArgsConstructor
public class SmsServiceImpl implements SmsService {

    private final SysConfigApi sysConfigApi;

    private final CacheService cacheService;

    private final SmsLogService smsLogService;

    private final SendSmsService sendSmsService;

    @Override
    public void sendSmsCode(SmsTemplateType smsTemplateType, String mobile) {
        this.smsLimitCheck(smsTemplateType.getValue(), mobile);
        String smsCode = StringUtil.randomNumber();
        this.doSendSms(mobile, smsTemplateType, smsCode);
        this.saveSmsCode(smsTemplateType.getValue(), mobile, smsCode);
        long expire = sysConfigApi.getLong(ConfigConstant.SMS_TYPE_INTERVAL);
        cacheService.setValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, smsTemplateType.getValue(), mobile), true, expire);
    }

    @Override
    public void sendSmsCode(SmsTemplateType smsTemplateType, String mobile, String ip) {
        this.smsIpLimitCheck(ip);
        this.sendSmsCode(smsTemplateType, mobile);
    }

    @Override
    public String getSmsCode(SmsTemplateType smsTemplateType, String mobile) {
        return cacheService.getValue(String.format(CacheConstant.SMS_PREFIX, smsTemplateType.getValue(), mobile));
    }

    @Override
    public String verifySmsCode(SmsTemplateType smsTemplateType, String mobile, String smsCode) {
        String originalSmsCode = this.getSmsCode(smsTemplateType, mobile);
        if (originalSmsCode == null) {
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_EXPIRE);
        }
        String key = smsTemplateType.getValue() + mobile;
        Long present = CacheUtil.SMS_VERIFY_CACHE.getIfPresent(key);
        if (present != null && present > MAX_ERROR_NUM) {
            this.cleanSmsCode(smsTemplateType, mobile);
            throw new BusinessException(ErrorCode.SMS_CODE_VERIFY_ERROR);
        }
        if (!originalSmsCode.equalsIgnoreCase(smsCode)) {
            CacheUtil.SMS_VERIFY_CACHE.put(key, present == null ? 1 : present + 1);
            throw new BusinessException(ErrorCode.LOGIN_SMS_CODE_ERROR);
        }
        this.cleanSmsCode(smsTemplateType, mobile);
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

    /**
     * 删除短信验证码
     *
     * @param smsTemplateType 短信类型
     * @param mobile  手机号码
     */
    private void cleanSmsCode(SmsTemplateType smsTemplateType, String mobile) {
        cacheService.delete(CacheConstant.SMS_PREFIX + smsTemplateType.getValue() + mobile);
    }

    /**
     * 发送短信并记录短信日志
     *
     * @param mobile  手机号
     * @param smsTemplateType 短信类型
     * @param params  参数
     */
    private void doSendSms(String mobile, SmsTemplateType smsTemplateType, String... params) {
        int state = sendSmsService.sendSms(mobile, smsTemplateType, params);
        SmsLog smsLog = SmsLog.builder().content(String.format(smsTemplateType.getContent(), (Object[]) params)).mobile(mobile).smsTemplateType(smsTemplateType).state(state).build();
        smsLogService.addSmsLog(smsLog);
    }

    /**
     * 保存发送的短信
     *
     * @param smsTemplateType 短信类型
     * @param mobile  手机号码
     * @param smsCode 短信验证码
     */
    private void saveSmsCode(String smsTemplateType, String mobile, String smsCode) {
        cacheService.setValue(CacheConstant.SMS_PREFIX + smsTemplateType + mobile, smsCode, sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600));
    }

    /**
     * 根据短信类型和手机号判断短信发送间隔及短信次数是否上限
     *
     * @param smsTemplateType 短信类型
     * @param mobile  手机号
     */
    private void smsLimitCheck(String smsTemplateType, String mobile) {
        // 短信时间间隔判断
        String value = cacheService.getValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, smsTemplateType, mobile));
        if (value != null) {
            throw new BusinessException(ErrorCode.SMS_FREQUENCY_FAST);
        }
        int smsTemplateTypeHourLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_HOUR_LIMIT);
        // 单位小时统一类型内短信限制
        boolean limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_HOUR_LIMIT, smsTemplateType, mobile), smsTemplateTypeHourLimit, 3600);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_HOUR_LIMIT);
        }
        int smsTemplateTypeDayLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY_LIMIT);
        // 当天同一类型短信限制
        limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_DAY_LIMIT, smsTemplateType, mobile), smsTemplateTypeDayLimit, 86400);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_DAY_LIMIT);
        }
        int smsDay = sysConfigApi.getInt(ConfigConstant.SMS_DAY_LIMIT);
        // 当天手机号限制
        limit = cacheService.limit(String.format(CacheConstant.SMS_DAY, mobile), smsDay, 86400);
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
