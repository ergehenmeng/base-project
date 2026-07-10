package com.eghm.application.system.service.impl;

import com.eghm.domain.shared.enums.UserState;
import com.eghm.domain.system.model.SysUser;
import com.eghm.application.shared.dto.sys.login.SmsLoginRequest;
import com.eghm.application.shared.dto.sys.login.TotpBindRequest;
import com.eghm.application.shared.dto.sys.login.TotpCheckRequest;
import com.eghm.application.shared.dto.sys.user.PasswordEditRequest;
import com.eghm.application.shared.dto.sys.user.UserAddRequest;
import com.eghm.application.shared.dto.sys.user.UserEditRequest;
import com.eghm.application.shared.dto.sys.user.UserProfileRequest;
import com.eghm.application.system.query.SysRoleQueryService;
import com.eghm.application.system.service.SysUserAuthApplicationService;
import com.eghm.application.system.service.SysUserCommandApplicationService;
import com.eghm.application.system.service.SysUserPasswordApplicationService;
import com.eghm.application.system.service.SysUserApplicationService;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.login.LoginMenuResponse;
import com.eghm.application.shared.vo.login.LoginResponse;
import com.eghm.application.shared.vo.login.TotpLoginResponse;
import com.eghm.application.shared.vo.sys.user.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统用户服务门面 - 委托给子服务处理具体业务
 *
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Slf4j
@Service("sysUserService")
@RequiredArgsConstructor
public class SysUserApplicationServiceImpl implements SysUserApplicationService {

    private final SysUserAuthApplicationService sysUserAuthService;
    private final SysUserPasswordApplicationService sysUserPasswordService;
    private final SysUserCommandApplicationService sysUserCommandService;
    private final SysRoleQueryService sysRoleQueryService;

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        sysUserPasswordService.updateLoginPassword(request);
    }

    @Override
    public void checkPassword(Long userId, String rawPassword) {
        sysUserPasswordService.checkPassword(userId, rawPassword);
    }

    @Override
    public void create(UserAddRequest request) {
        sysUserCommandService.create(request);
    }

    @Override
    public SysUser getByIdRequired(Long id) {
        return sysUserCommandService.getByIdRequired(id);
    }

    @Override
    public UserDetailResponse getDetailById(Long id) {
        SysUser user = sysUserCommandService.getByIdRequired(id);
        UserDetailResponse response = DataUtil.copy(user, UserDetailResponse.class);
        List<Long> roleList = sysRoleQueryService.listRoleIdsByUserId(id);
        response.setRoleIds(roleList);
        return response;
    }

    @Override
    public void update(UserEditRequest request) {
        sysUserCommandService.update(request);
    }

    @Override
    public void resetPassword(Long id) {
        sysUserPasswordService.resetPassword(id);
    }

    @Override
    public void deleteById(Long id) {
        sysUserCommandService.deleteById(id);
    }

    @Override
    public void updateState(Long id, UserState state) {
        sysUserCommandService.updateState(id, state);
    }

    @Override
    public TotpLoginResponse login(String userName, String password, String openId) {
        return sysUserAuthService.login(userName, password, openId);
    }

    @Override
    public LoginResponse checkTotp(TotpCheckRequest request) {
        return sysUserAuthService.checkTotp(request);
    }

    @Override
    public LoginResponse bindTotp(TotpBindRequest request) {
        return sysUserAuthService.bindTotp(request);
    }

    @Override
    public void unbindWeChat() {
        sysUserAuthService.unbindWeChat();
    }

    @Override
    public void sendLoginSms(String mobile, String ip) {
        sysUserAuthService.sendLoginSms(mobile, ip);
    }

    @Override
    public LoginResponse smsLogin(SmsLoginRequest request, String openId) {
        return sysUserAuthService.smsLogin(request, openId);
    }

    @Override
    public SysUser getByOpenId(String openId) {
        return sysUserAuthService.getByOpenId(openId);
    }

    @Override
    public LoginResponse doLogin(SysUser user) {
        return sysUserAuthService.doLogin(user);
    }

    @Override
    public LoginMenuResponse getPermission() {
        return sysUserAuthService.getPermission();
    }

    @Override
    public void unBindTotp(Long userId) {
        sysUserAuthService.unBindTotp(userId);
    }

    @Override
    public void updateAvatar(Long userId, String avatar) {
        sysUserCommandService.updateAvatar(userId, avatar);
    }

    @Override
    public void updateProfile(UserProfileRequest request) {
        sysUserCommandService.updateProfile(request);
    }
}
