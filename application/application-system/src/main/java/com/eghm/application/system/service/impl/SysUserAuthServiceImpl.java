package com.eghm.application.system.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.common.SmsService;
import com.eghm.application.shared.common.UserTokenService;
import com.eghm.application.shared.common.impl.SysConfigApi;
import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.configuration.encoder.Encoder;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.TemplateType;
import com.eghm.domain.shared.enums.UserState;
import com.eghm.domain.shared.enums.UserType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.system.model.SysUser;
import com.eghm.domain.system.repository.SysUserRepository;
import com.eghm.application.shared.dto.ext.UserToken;
import com.eghm.application.shared.dto.sys.login.SmsLoginRequest;
import com.eghm.application.shared.dto.sys.login.TotpBindRequest;
import com.eghm.application.shared.dto.sys.login.TotpCheckRequest;
import com.eghm.application.system.port.in.SysDeptDataService;
import com.eghm.application.system.port.in.SysMenuService;
import com.eghm.application.system.port.in.SysUserAuthService;
import com.eghm.application.system.port.in.SysUserCommandService;
import com.eghm.application.shared.manager.LoginCacheManager;
import com.eghm.application.shared.utils.TotpUtil;
import com.eghm.application.shared.vo.login.LoginMenuResponse;
import com.eghm.application.shared.vo.login.LoginResponse;
import com.eghm.application.shared.vo.login.TotpLoginResponse;
import com.eghm.application.shared.vo.sys.menu.MenuTreeResponse;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CommonConstant.MAX_ERROR_NUM;

/**
 * 系统用户认证服务实现
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserAuthServiceImpl implements SysUserAuthService {

    private final Encoder encoder;
    private final SmsService smsService;
    private final SysConfigApi sysConfigApi;
    private final SysUserRepository sysUserRepository;
    private final CommonService commonService;
    private final SysMenuService sysMenuService;
    private final UserTokenService userTokenService;
    private final LoginCacheManager loginCacheManager;
    private final SysDeptDataService sysDeptDataService;
    private final SysUserCommandService sysUserCommandService;

    @Override
    public TotpLoginResponse login(String userName, String password, String openId) {
        SysUser user = this.getAndCheckUser(userName, password);
        this.tryBindingOpenId(user.getId(), openId);
        boolean openTotp = sysConfigApi.getBoolean(ConfigConstant.OPEN_TOTP);
        if (openTotp) {
            String uuid = IdUtil.simpleUUID();
            loginCacheManager.saveTotpData(uuid, user.getId());
            if (user.getTotpSecret() == null) {
                GoogleAuthenticatorKey secretKey = TotpUtil.createSecretKey();
                String generated = this.generateTotpUrl(user.getUserName(), secretKey);
                return TotpLoginResponse.needBindTotp(uuid, QrCodeUtil.generateAsBase64(generated, QrConfig.create(), "png"), secretKey.getKey());
            }
            return TotpLoginResponse.needTotp(uuid);
        }
        LoginResponse response = this.doLogin(user);
        return TotpLoginResponse.success(response);
    }

    @Override
    public LoginResponse smsLogin(SmsLoginRequest request, String openId) {
        smsService.verifySmsCode(TemplateType.USER_LOGIN, request.getMobile(), request.getSmsCode());
        SysUser user = this.getAndCheckUser(request.getMobile());
        this.tryBindingOpenId(user.getId(), openId);
        return this.doLogin(user);
    }

    @Override
    public void sendLoginSms(String mobile, String ip) {
        SysUser user = this.getAndCheckUser(mobile);
        smsService.sendSmsCode(TemplateType.USER_LOGIN, user.getMobile(), ip);
    }

    @Override
    public LoginResponse checkTotp(TotpCheckRequest request) {
        SysUser user = this.getByUuid(request.getUuid());
        if (TotpUtil.invalid(user.getTotpSecret(), request.getVerifyCode())) {
            throw new BusinessException(ErrorCode.TOTP_SN_ERROR);
        }
        LoginResponse response = this.doLogin(user);
        loginCacheManager.clearTotpData(request.getUuid());
        return response;
    }

    @Override
    public LoginResponse bindTotp(TotpBindRequest request) {
        if (TotpUtil.invalid(request.getSecretKey(), request.getVerifyCode())) {
            throw new BusinessException(ErrorCode.TOTP_SN_ERROR);
        }
        SysUser user = this.getByUuid(request.getUuid());
        user.bindTotp(request.getSecretKey());
        sysUserRepository.updateTotpSecret(user.getId(), user.getTotpSecret());
        loginCacheManager.clearTotpData(request.getUuid());
        return this.doLogin(user);
    }

    @Override
    public void unbindWeChat() {
        sysUserRepository.clearOpenId(SecurityHolder.getUserId());
    }

    @Override
    public void unBindTotp(Long userId) {
        sysUserRepository.clearTotpSecret(userId);
    }

    @Override
    public SysUser getByOpenId(String openId) {
        return sysUserRepository.findByOpenId(openId);
    }

    @Override
    public LoginResponse doLogin(SysUser user) {
        List<String> customList = sysDeptDataService.getDeptList(user.getId());
        String token = userTokenService.createToken(user, customList);
        LoginResponse response = this.buildLoginResponse(user, token);
        loginCacheManager.clearAllLoginCache(user);
        return response;
    }

    @Override
    public LoginMenuResponse getPermission() {
        UserToken userToken = SecurityHolder.getUserRequired();
        LoginMenuResponse response = new LoginMenuResponse();
        List<MenuTreeResponse> leftMenu;
        List<String> buttonList;
        if (userToken.getUserType() == UserType.ADMINISTRATOR) {
            leftMenu = sysMenuService.getAdminLeftMenuList();
            buttonList = sysMenuService.getAdminPermCode();
        } else {
            buttonList = sysMenuService.getPermCode(userToken.getId());
            leftMenu = sysMenuService.getLeftMenuList(userToken.getId());
        }
        commonService.savePermission(userToken.getToken(), buttonList);
        response.setMenuList(leftMenu);
        response.setPermList(buttonList);
        return response;
    }

    private LoginResponse buildLoginResponse(SysUser user, String token) {
        LoginResponse response = new LoginResponse();
        response.setAvatar(user.getAvatar());
        response.setToken(token);
        response.setMobile(user.getMobile());
        response.setSystemName(sysConfigApi.getString(ConfigConstant.SYSTEM_NAME));
        response.setNickName(user.getNickName());
        response.setUserType(user.getUserType());
        response.setInit(user.isInitialPassword());
        response.setExpire(user.isPasswordExpire(LocalDateTime.now(), CommonConstant.PWD_UPDATE_TIPS));
        return response;
    }

    private String generateTotpUrl(String userName, GoogleAuthenticatorKey secretKey) {
        String systemName = sysConfigApi.getString(ConfigConstant.SYSTEM_NAME);
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(systemName, userName, secretKey);
    }

    private SysUser getByUuid(String uuid) {
        Long userId = loginCacheManager.getTotpUserId(uuid);
        if (userId == null) {
            throw new BusinessException(ErrorCode.TOTP_SN_EXPIRE);
        }
        return sysUserCommandService.getByIdRequired(userId);
    }

    private void tryBindingOpenId(Long id, String openId) {
        if (openId != null) {
            SysUser user = new SysUser();
            user.setId(id);
            user.bindOpenId(openId);
            sysUserRepository.updateOpenId(user.getId(), user.getOpenId());
        }
    }

    private SysUser getByAccount(String userName) {
        if (PhoneUtil.isMobile(userName)) {
            return sysUserRepository.findByMobile(userName);
        }
        return sysUserRepository.findByUserName(userName);
    }

    private SysUser getAndCheckUser(String userName, String password) {
        int present = loginCacheManager.getLoginErrorCount(userName);
        if (present > MAX_ERROR_NUM) {
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
        SysUser user = this.getByAccount(userName);
        if (user == null || user.getState() == UserState.LOGOUT || !encoder.match(SecureUtil.sha256(password), user.getPwd())) {
            loginCacheManager.incrementLoginError(userName);
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        user.assertCanPasswordLogin();
        return user;
    }

    private SysUser getAndCheckUser(String mobile) {
        if (loginCacheManager.isLocked(mobile)) {
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
        SysUser user = sysUserRepository.findByMobile(mobile);
        if (user == null || user.getState() == UserState.LOGOUT) {
            throw new BusinessException(ErrorCode.USER_MOBILE_NULL);
        }
        user.assertCanSmsLogin();
        return user;
    }
}
