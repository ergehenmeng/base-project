package com.eghm.application.member.service;

import com.eghm.dto.sys.register.AccountRegisterDTO;
import com.eghm.dto.sys.register.MobileRegisterDTO;
import com.eghm.vo.login.LoginTokenVO;

/**
 * 会员注册服务 - 负责所有注册相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
public interface MemberRegisterService {

    void registerSendSms(String mobile, String ip);

    LoginTokenVO registerByMobile(MobileRegisterDTO request);

    LoginTokenVO registerByAccount(AccountRegisterDTO dto);

    LoginTokenVO mpLogin(String jsCode, String ip);

    LoginTokenVO maLogin(String jsCode, String openId, String ip);

    LoginTokenVO maLogin(String openId, String ip);
}
