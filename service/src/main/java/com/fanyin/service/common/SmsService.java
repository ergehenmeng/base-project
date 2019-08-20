package com.fanyin.service.common;

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
    void sendSms(String smsType,String mobile);

    /**
     * 获取短信验证码
     * @param smsType 验证码类型
     * @param mobile 手机号码
     * @return 短信验证码
     */
    String getSmsCode(String smsType,String mobile);
}
