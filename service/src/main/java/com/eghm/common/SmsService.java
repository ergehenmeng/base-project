package com.eghm.common;

import com.eghm.enums.SmsType;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:46
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param smsType 短信验证码类型
     * @param mobile  手机号
     * @param ip      ip
     */
    void sendSmsCode(SmsType smsType, String mobile, String ip);

    /**
     * 验证短信验证码是否正确 (如果验证码正确,会将缓存中验证码删除,并生成唯一ID,再次放入缓存中,方便后续业务)
     * 同样如果验证次数太多,依旧会返回验证失败
     * @param smsType 验证码类型
     * @param mobile  手机号
     * @param smsCode 待验证的验证码
     * @return requestId:是该短信验证码验证通过的唯一凭证
     */
    String verifySmsCode(SmsType smsType, String mobile, String smsCode);

    /**
     * 发送短信并记录短信日志
     *
     * @param mobile  手机号
     * @param smsType 短信类型
     * @param params  参数
     */
    void sendSms(String mobile, SmsType smsType, String... params);

    /**
     * 验证短信验证码凭证是否正确
     *
     * @param requestId requestId
     * @return true:凭证正确, false:凭证错误
     */
    boolean verifyRequestId(String requestId);

}
