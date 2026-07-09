package com.eghm.application.system.service;

import com.eghm.domain.system.model.SysUser;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.login.TotpBindRequest;
import com.eghm.dto.sys.login.TotpCheckRequest;
import com.eghm.vo.login.LoginMenuResponse;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.login.TotpLoginResponse;

/**
 * 系统用户认证服务 - 负责登录、TOTP、权限相关操作
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
public interface SysUserAuthService {

    TotpLoginResponse login(String userName, String password, String openId);

    LoginResponse smsLogin(SmsLoginRequest request, String openId);

    void sendLoginSms(String mobile, String ip);

    LoginResponse checkTotp(TotpCheckRequest request);

    LoginResponse bindTotp(TotpBindRequest request);

    void unbindWeChat();

    void unBindTotp(Long userId);

    SysUser getByOpenId(String openId);

    LoginResponse doLogin(SysUser user);

    LoginMenuResponse getPermission();
}
