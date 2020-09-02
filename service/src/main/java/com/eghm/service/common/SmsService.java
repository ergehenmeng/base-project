package com.eghm.service.common;

import com.eghm.common.enums.SmsType;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
public interface SmsService {

    /**
     * 发送短信验证码
     * @param smsType 短信验证码类型
     * @param mobile 手机号
     */
    void sendSmsCode(SmsType smsType, String mobile);

    /**
     * 获取短信验证码
     * @param smsType 验证码类型
     * @param mobile 手机号码
     * @return 短信验证码
     */
    String getSmsCode(SmsType smsType,String mobile);

    /**
     * 验证短信验证码是否正确
     * @param smsType 验证码类型
     * @param mobile 手机号
     * @param smsCode 待验证的验证码
     */
    void verifySmsCode(SmsType smsType,String mobile ,String smsCode);

    /**
     * 根据短信模板发送短信 (不需要次数校验)
     * @param smsType 短信类型
     * @param mobile 手机号
     * @param params 短信模板参数
     */
    void sendSms(SmsType smsType,String mobile,Object... params);

    /**
     * 直接发送短信 (不需要校验次数)
     * @param mobile 手机号
     * @param content 短信内容
     */
    void sendSms(String mobile,String content);

    /**
     * 批量发送短信,一般为推广短信
     * @param mobileList 手机号列表
     * @param content 短信内容
     */
    void sendSms(List<String> mobileList, String content);
}
