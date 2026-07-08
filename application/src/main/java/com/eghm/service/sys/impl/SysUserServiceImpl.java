package com.eghm.service.sys.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.eghm.dto.ext.Page;
import com.eghm.common.CommonService;
import com.eghm.common.SmsService;
import com.eghm.common.UserTokenService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.login.TotpBindRequest;
import com.eghm.dto.sys.login.TotpCheckRequest;
import com.eghm.dto.sys.user.PasswordEditRequest;
import com.eghm.dto.sys.user.UserAddRequest;
import com.eghm.dto.sys.user.UserEditRequest;
import com.eghm.dto.sys.user.UserProfileRequest;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.enums.DataType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.TemplateType;
import com.eghm.enums.UserState;
import com.eghm.enums.UserType;
import com.eghm.exception.BusinessException;
import com.eghm.manager.LoginCacheManager;
import com.eghm.service.sys.SysDeptDataService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserQueryGateway;
import com.eghm.service.sys.SysUserService;
import com.eghm.sys.model.SysDeptData;
import com.eghm.sys.model.SysUser;
import com.eghm.sys.repository.SysUserRepository;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TotpUtil;
import com.eghm.vo.login.LoginMenuResponse;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.login.TotpLoginResponse;
import com.eghm.vo.sys.menu.MenuTreeResponse;
import com.eghm.vo.sys.user.UserResponse;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CommonConstant.MAX_ERROR_NUM;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Slf4j
@AllArgsConstructor
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final Encoder encoder;

    private final SmsService smsService;

    private final SysConfigApi sysConfigApi;

    private final SysUserRepository sysUserRepository;

    private final SysUserQueryGateway sysUserQueryGateway;

    private final CommonService commonService;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final UserTokenService userTokenService;

    private final LoginCacheManager loginCacheManager;

    private final SysDeptDataService sysDeptDataService;

    @Override
    public Page<UserResponse> getByPage(UserQueryRequest request) {
        return sysUserQueryGateway.getByPage(request);
    }

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        SysUser user = this.getByIdRequired(request.getUserId());
        this.checkPassword(SecureUtil.sha256(request.getOldPwd()), user.getPwd());
        String newPassword = encoder.encode(SecureUtil.sha256(request.getNewPwd()));
        user.changePassword(newPassword, LocalDateTime.now());
        sysUserRepository.updatePassword(user.getId(), user.getPwd(), user.getPwdUpdateTime());
    }

    @Override
    public void checkPassword(Long userId, String rawPassword) {
        SysUser user = this.getByIdRequired(userId);
        boolean match = encoder.match(rawPassword, user.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
    }

    @Override
    public void create(UserAddRequest request) {
        checkUserName(request.getUserName(), null);
        checkMobile(request.getMobile(), null);
        SysUser user = DataUtil.copy(request, SysUser.class);
        String password = this.initPassword(request.getMobile());
        user.initializeSystemUser(password, LocalDateTime.now());
        sysUserRepository.save(user);
        sysRoleService.auth(user.getId(), request.getRoleIds());
        if (request.getDataType() == DataType.CUSTOM) {
            request.getDeptIds().forEach(deptId -> sysDeptDataService.insert(new SysDeptData(user.getId(), deptId)));
        }
    }

    @Override
    public SysUser getByIdRequired(Long id) {
        SysUser user = sysUserRepository.findById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public void update(UserEditRequest request) {
        checkUserName(request.getUserName(), request.getId());
        checkMobile(request.getMobile(), request.getId());
        SysUser user = DataUtil.copy(request, SysUser.class);
        sysUserRepository.update(user);
        sysRoleService.auth(user.getId(), request.getRoleIds());
        if (request.getDataType() != null && request.getDataType() == DataType.CUSTOM) {
            sysDeptDataService.deleteByUserId(user.getId());
            request.getDeptIds().forEach(deptId -> sysDeptDataService.insert(new SysDeptData(user.getId(), deptId)));
        }
    }

    @Override
    public void resetPassword(Long id) {
        SysUser user = this.getByIdRequired(id);
        String password = this.initPassword(user.getMobile());
        user.resetPassword(password, LocalDateTime.now());
        sysUserRepository.resetPassword(user.getId(), user.getPwd(), user.getPwdUpdateTime());
        loginCacheManager.clearLoginLockCache(user.getUserName(), user.getMobile());
    }

    @Override
    public void deleteById(Long id) {
        sysUserRepository.deleteById(id);
    }

    @Override
    public void updateState(Long id, UserState state) {
        sysUserRepository.updateState(id, state);
    }

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
    public void sendLoginSms(String mobile, String ip) {
        SysUser user = this.getAndCheckUser(mobile);
        smsService.sendSmsCode(TemplateType.USER_LOGIN, user.getMobile(), ip);
    }

    @Override
    public LoginResponse smsLogin(SmsLoginRequest request, String openId) {
        smsService.verifySmsCode(TemplateType.USER_LOGIN, request.getMobile(), request.getSmsCode());
        SysUser user = this.getAndCheckUser(request.getMobile());
        this.tryBindingOpenId(user.getId(), openId);
        return this.doLogin(user);
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

    @Override
    public void unBindTotp(Long userId) {
        sysUserRepository.clearTotpSecret(userId);
    }

    @Override
    public void updateAvatar(Long userId, String avatar) {
        sysUserRepository.updateAvatar(userId, avatar);
    }

    @Override
    public void updateProfile(UserProfileRequest request) {
        sysUserRepository.updateProfile(request.getUserId(), request.getNickName(), request.getMobile());
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
        return this.getByIdRequired(userId);
    }

    private void tryBindingOpenId(Long id, String openId) {
        if (openId != null) {
            SysUser user = new SysUser();
            user.setId(id);
            user.bindOpenId(openId);
            sysUserRepository.updateOpenId(user.getId(), user.getOpenId());
        }
    }

    private String initPassword(String mobile) {
        String rsaPassword = SecureUtil.sha256(mobile.substring(3));
        return encoder.encode(rsaPassword);
    }

    private void checkPassword(String rawPassword, String targetPassword) {
        boolean match = encoder.match(rawPassword, targetPassword);
        if (!match) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
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

    private void checkUserName(String userName, Long excludeId) {
        if (sysUserRepository.existsUserName(userName, excludeId)) {
            log.warn("账户名被占用 [{}] [{}]", excludeId, userName);
            throw new BusinessException(ErrorCode.USER_NAME_REDO);
        }
    }

    private void checkMobile(String mobile, Long excludeId) {
        if (sysUserRepository.existsMobile(mobile, excludeId)) {
            log.warn("手机号码被占用 [{}] [{}]", excludeId, mobile);
            throw new BusinessException(ErrorCode.MOBILE_REDO);
        }
    }
}
