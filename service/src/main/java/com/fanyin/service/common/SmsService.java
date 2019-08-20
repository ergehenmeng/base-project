package com.fanyin.service.common;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:46
 */
public interface SmsService {

    /**
     * 获取短信验证码
     * @param smsType 验证码类型
     * @param mobile 手机号码
     * @return 短信验证码
     */
    String getSmsCode(String smsType,String mobile);
}
