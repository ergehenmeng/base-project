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
     * 验证短信验证码是否正确 (如果验证码正确,会将缓存中验证码删除,并生成唯一ID,再次放入缓存中,方便后续业务)
     * @param smsType 验证码类型
     * @param mobile 手机号
     * @param smsCode 待验证的验证码
     * @return requestId:是该短信验证码验证通过的唯一凭证
     */
    String verifySmsCode(SmsType smsType,String mobile ,String smsCode);

    /**
     * 验证短信验证码凭证是否正确
     * @param requestId requestId
     * @return true:凭证正确, false:凭证错误
     */
    boolean verifyRequestId(String requestId);

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
