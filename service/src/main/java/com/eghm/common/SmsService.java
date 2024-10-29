package com.eghm.common;

import com.eghm.enums.SmsTemplateType;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:46
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param smsTemplateType 短信验证码类型
     * @param mobile  手机号
     */
    void sendSmsCode(SmsTemplateType smsTemplateType, String mobile);

    /**
     * 发送短信验证码
     *
     * @param smsTemplateType 短信验证码类型
     * @param mobile  手机号
     * @param ip      ip
     */
    void sendSmsCode(SmsTemplateType smsTemplateType, String mobile, String ip);

    /**
     * 获取短信验证码
     *
     * @param smsTemplateType 验证码类型
     * @param mobile  手机号码
     * @return 短信验证码
     */
    String getSmsCode(SmsTemplateType smsTemplateType, String mobile);

    /**
     * 验证短信验证码是否正确 (如果验证码正确,会将缓存中验证码删除,并生成唯一ID,再次放入缓存中,方便后续业务)
     * 同样如果验证次数太多,依旧会返回验证失败
     * @param smsTemplateType 验证码类型
     * @param mobile  手机号
     * @param smsCode 待验证的验证码
     * @return requestId:是该短信验证码验证通过的唯一凭证
     */
    String verifySmsCode(SmsTemplateType smsTemplateType, String mobile, String smsCode);

    /**
     * 验证短信验证码凭证是否正确
     *
     * @param requestId requestId
     * @return true:凭证正确, false:凭证错误
     */
    boolean verifyRequestId(String requestId);

}
