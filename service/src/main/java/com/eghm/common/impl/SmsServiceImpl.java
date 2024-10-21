package com.eghm.common.impl;

import cn.hutool.core.util.IdUtil;
import com.eghm.cache.CacheService;
import com.eghm.common.SendSmsService;
import com.eghm.common.SmsService;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SmsType;
import com.eghm.exception.BusinessException;
import com.eghm.model.SmsLog;
import com.eghm.service.sys.SmsLogService;
import com.eghm.service.sys.SmsTemplateService;
import com.eghm.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

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

    private final SmsTemplateService smsTemplateService;

    @Override
    public void sendSmsCode(SmsType smsType, String mobile) {
        this.smsLimitCheck(smsType.getValue(), mobile);
        String template = smsTemplateService.getTemplate(smsType.getValue());
        String smsCode = StringUtil.randomNumber();
        String content = this.formatTemplate(template, smsCode);
        this.doSendSms(mobile, content, smsType);
        this.saveSmsCode(smsType.getValue(), mobile, smsCode);
        long expire = sysConfigApi.getLong(ConfigConstant.SMS_TYPE_INTERVAL);
        cacheService.setValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, smsType.getValue(), mobile), true, expire);
    }

    @Override
    public void sendSmsCode(SmsType smsType, String mobile, String ip) {
        this.smsIpLimitCheck(ip);
        this.sendSmsCode(smsType, mobile);
    }

    @Override
    public String getSmsCode(SmsType smsType, String mobile) {
        return cacheService.getValue(String.format(CacheConstant.SMS_PREFIX, smsType.getValue(), mobile));
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
        String uuid = IdUtil.fastSimpleUUID();
        cacheService.setValue(CacheConstant.VERIFY_PREFIX + uuid, true, 300);
        cacheService.setValue(CacheConstant.VERIFY_MOBILE_PREFIX + uuid, mobile, 300);
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
    public void sendSms(SmsType smsType, String mobile, Object... params) {
        String template = smsTemplateService.getTemplate(smsType.getValue());
        if (template == null) {
            log.warn("短信模板不存在或已禁用:[{}] [{}] [{}]", smsType, mobile, params);
            return;
        }
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
     *
     * @param smsType 短信类型
     * @param mobile  手机号码
     */
    private void cleanSmsCode(SmsType smsType, String mobile) {
        cacheService.delete(CacheConstant.SMS_PREFIX + smsType.getValue() + mobile);
    }

    /**
     * 发送短信并记录短信日志
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @param smsType 短信类型
     */
    private void doSendSms(String mobile, String content, SmsType smsType) {
        int state = sendSmsService.sendSms(mobile, content);
        SmsLog smsLog = SmsLog.builder().content(content).mobile(mobile).smsType(smsType).state(state).build();
        smsLogService.addSmsLog(smsLog);
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

    /**
     * 保存发送的短信
     *
     * @param smsType 短信类型
     * @param mobile  手机号码
     * @param smsCode 短信验证码
     */
    private void saveSmsCode(String smsType, String mobile, String smsCode) {
        cacheService.setValue(CacheConstant.SMS_PREFIX + smsType + mobile, smsCode, sysConfigApi.getLong(ConfigConstant.AUTH_CODE_EXPIRE, 600));
    }

    /**
     * 根据短信类型和手机号判断短信发送间隔及短信次数是否上限
     *
     * @param smsType 短信类型
     * @param mobile  手机号
     */
    private void smsLimitCheck(String smsType, String mobile) {
        // 短信时间间隔判断
        String value = cacheService.getValue(String.format(CacheConstant.SMS_TYPE_INTERVAL, smsType, mobile));
        if (value != null) {
            throw new BusinessException(ErrorCode.SMS_FREQUENCY_FAST);
        }
        int smsTypeHourLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_HOUR_LIMIT);
        // 单位小时统一类型内短信限制
        boolean limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_HOUR_LIMIT, smsType, mobile), smsTypeHourLimit, 3600);
        if (limit) {
            throw new BusinessException(ErrorCode.SMS_HOUR_LIMIT);
        }
        int smsTypeDayLimit = sysConfigApi.getInt(ConfigConstant.SMS_TYPE_DAY_LIMIT);
        // 当天同一类型短信限制
        limit = cacheService.limit(String.format(CacheConstant.SMS_TYPE_DAY_LIMIT, smsType, mobile), smsTypeDayLimit, 86400);
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
