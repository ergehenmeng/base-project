package com.eghm.common;

import com.eghm.enums.TemplateType;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:46
 */
public interface SmsService {

    /**
     * 发送短信验证码
     *
     * @param templateType 短信验证码类型
     * @param mobile       手机号
     * @param ip           ip
     */
    void sendSmsCode(TemplateType templateType, String mobile, String ip);

    /**
     * 验证短信验证码是否正确 (如果验证码正确,会将缓存中验证码删除,并生成唯一ID,再次放入缓存中,方便后续业务)
     * 同样如果验证次数太多,依旧会返回验证失败. 该功能使用场景是在多步骤提交表单时,可以先验证短信验证码,然后根据requestId进行后续业务处理,防止重放攻击.
     *
     * @param templateType 验证码类型
     * @param mobile       手机号
     * @param smsCode      待验证的验证码
     * @return requestId 验证码验证通过后即返回该凭证
     */
    String verifySmsCode(TemplateType templateType, String mobile, String smsCode);

    /**
     * 验证短信验证码凭证是否正确
     *
     * @param requestId requestId
     * @return true:凭证正确, false:凭证错误
     */
    boolean verifyRequestId(String requestId);

}
