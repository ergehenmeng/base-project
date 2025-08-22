package com.eghm.service.sys.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.CommonService;
import com.eghm.common.SmsService;
import com.eghm.common.UserTokenService;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.login.TotpBindRequest;
import com.eghm.dto.sys.login.TotpCheckRequest;
import com.eghm.dto.sys.user.PasswordEditRequest;
import com.eghm.dto.sys.user.UserAddRequest;
import com.eghm.dto.sys.user.UserEditRequest;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.enums.*;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysUserMapper;
import com.eghm.model.SysDataDept;
import com.eghm.model.SysUser;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TotpUtil;
import com.eghm.vo.login.LoginMenuResponse;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.login.TotpLoginResponse;
import com.eghm.vo.sys.menu.MenuResponse;
import com.eghm.vo.sys.user.UserResponse;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CommonConstant.MAX_ERROR_NUM;
import static com.eghm.utils.CacheUtil.LOGIN_LOCK_CACHE;
import static com.eghm.utils.CacheUtil.TOTP_CACHE;

/**
 * @author 二哥很猛
 * @since 2018/11/26 10:24
 */
@Service("sysUserService")
@AllArgsConstructor
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    private final Encoder encoder;

    private final SmsService smsService;

    private final CacheService cacheService;

    private final SysConfigApi sysConfigApi;

    private final SysUserMapper sysUserMapper;

    private final CommonService commonService;

    private final SysRoleService sysRoleService;

    private final SysMenuService sysMenuService;

    private final UserTokenService userTokenService;

    private final SysDataDeptService sysDataDeptService;

    @Override
    public Page<UserResponse> getByPage(UserQueryRequest request) {
        request.setUserType(UserType.SYS_USER.getValue());
        return sysUserMapper.listPage(request.createPage(), request);
    }

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        SysUser user = sysUserMapper.selectById(request.getUserId());
        this.checkPassword(SecureUtil.sha256(request.getOldPwd()), user.getPwd());
        String newPassword = encoder.encode(SecureUtil.sha256(request.getNewPwd()));
        user.setPwd(newPassword);
        user.setPwdUpdateTime(LocalDateTime.now());
        sysUserMapper.updateById(user);
    }

    @Override
    public void checkPassword(Long userId, String rawPassword) {
        SysUser user = sysUserMapper.selectById(userId);
        boolean match = encoder.match(rawPassword, user.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }
    }

    @Override
    public void create(UserAddRequest request) {
        this.redoUserName(request.getUserName(), null);
        this.redoMobile(request.getMobile(), null);
        SysUser user = DataUtil.copy(request, SysUser.class);
        user.setState(UserState.NORMAL);
        user.setUserType(UserType.SYS_USER);
        String password = this.initPassword(request.getMobile());
        user.setPwd(password);
        user.setInitPwd(password);
        user.setPwdUpdateTime(LocalDateTime.now());
        sysUserMapper.insert(user);
        // 角色权限
        sysRoleService.auth(user.getId(), request.getRoleIds());
        // 数据权限
        if (request.getDataType() == DataType.CUSTOM) {
            request.getDeptIds().forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
        }
    }

    @Override
    public SysUser getByIdRequired(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public void update(UserEditRequest request) {
        this.redoUserName(request.getUserName(), request.getId());
        this.redoMobile(request.getMobile(), request.getId());
        SysUser user = DataUtil.copy(request, SysUser.class);
        sysUserMapper.updateById(user);
        // 角色权限
        sysRoleService.auth(user.getId(), request.getRoleIds());
        // 数据权限, 在新增系统用户时,可以手动指定数据权限,此处既是将用户与其所拥有的的部门权限做关联,方便后续进行数据权限分组
        if (request.getDataType() != null && request.getDataType() == DataType.CUSTOM.getValue()) {
            // 删除旧数据权限
            sysDataDeptService.deleteByUserId(user.getId());
            // 添加新数据权限
            request.getDeptIds().forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
        }
    }

    @Override
    public void resetPassword(Long id) {
        SysUser user = sysUserMapper.selectById(id);
        String password = this.initPassword(user.getMobile());
        user.setPwd(password);
        user.setInitPwd(password);
        user.setPwdUpdateTime(LocalDateTime.now());
        sysUserMapper.updateById(user);
        LOGIN_LOCK_CACHE.invalidate(user.getUserName());
        LOGIN_LOCK_CACHE.invalidate(user.getMobile());
    }

    @Override
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public void updateState(Long id, UserState state) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setState(state);
        sysUserMapper.updateById(user);
    }

    @Override
    public TotpLoginResponse login(String userName, String password, String openId) {
        SysUser user = this.getAndCheckUser(userName, password);
        this.tryBindingOpenId(user.getId(), openId);
        boolean openTotp = sysConfigApi.getBoolean(ConfigConstant.OPEN_TOTP);
        if (openTotp) {
            String uuid = IdUtil.simpleUUID();
            TOTP_CACHE.put(uuid, user.getId());
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
        TOTP_CACHE.invalidate(request.getUuid());
        return response;
    }

    @Override
    public LoginResponse bindTotp(TotpBindRequest request) {
        if (TotpUtil.invalid(request.getSecretKey(), request.getVerifyCode())) {
            throw new BusinessException(ErrorCode.TOTP_SN_ERROR);
        }
        SysUser user = this.getByUuid(request.getUuid());
        user.setTotpSecret(request.getSecretKey());
        sysUserMapper.updateById(user);
        TOTP_CACHE.invalidate(request.getUuid());
        return this.doLogin(user);
    }

    @Override
    public void unbindWeChat() {
        LambdaUpdateWrapper<SysUser> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUser::getId, SecurityHolder.getUserId());
        wrapper.set(SysUser::getOpenId, null);
        sysUserMapper.update(null, wrapper);
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
        return sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getOpenId, openId));
    }

    @Override
    public LoginResponse doLogin(SysUser user) {
        // 数据权限(此处没有判断,逻辑不够严谨,仅仅为了代码简洁)
        List<String> customList = sysDataDeptService.getDeptList(user.getId());
        String token = userTokenService.createToken(user, customList);
        String systemName = sysConfigApi.getString(ConfigConstant.SYSTEM_NAME);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setBindWechat(user.getOpenId() != null);
        response.setUserName(user.getUserName());
        response.setSystemName(systemName);
        response.setNickName(user.getNickName());
        response.setUserType(user.getUserType());
        response.setInit(user.getInitPwd().equals(user.getPwd()));
        response.setExpire(user.getPwdUpdateTime().plusDays(CommonConstant.PWD_UPDATE_TIPS).isBefore(LocalDateTime.now()));
        cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
        LOGIN_LOCK_CACHE.invalidate(user.getMobile());
        LOGIN_LOCK_CACHE.invalidate(user.getUserName());
        return response;
    }

    @Override
    public LoginMenuResponse getPermission() {
        UserToken userToken = SecurityHolder.getUserRequired();
        LoginMenuResponse response = new LoginMenuResponse();
        // 如果用户拥有超管角色,则默认查询全部菜单等信息
        List<MenuResponse> leftMenu;
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
        LambdaUpdateWrapper<SysUser> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(SysUser::getId, userId);
        wrapper.set(SysUser::getTotpSecret, null);
        sysUserMapper.update(null, wrapper);
    }

    /**
     * 生成totpUrl
     *
     * @param userName  用户名
     * @param secretKey 密钥
     * @return totpUrl otpauth://totp/systemName:userName?secret=ef7umz73ppab2w7wyhnwpgpw3caotwfa&issuer=systemName
     */
    private String generateTotpUrl(String userName, GoogleAuthenticatorKey secretKey) {
        String systemName = sysConfigApi.getString(ConfigConstant.SYSTEM_NAME);
        return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(systemName, userName, secretKey);
    }

    /**
     * 根据uuid获取用户信息
     *
     * @param uuid 绑定totp时生成的uuid
     * @return 用户信息
     */
    private SysUser getByUuid(String uuid) {
        Long userId = TOTP_CACHE.getIfPresent(uuid);
        if (userId == null) {
            throw new BusinessException(ErrorCode.TOTP_SN_EXPIRE);
        }
        return this.getByIdRequired(userId);
    }

    /**
     * 尝试绑定openId
     *
     * @param id     id
     * @param openId openId
     */
    private void tryBindingOpenId(Long id, String openId) {
        if (openId != null) {
            SysUser user = new SysUser();
            user.setId(id);
            user.setOpenId(openId);
            sysUserMapper.updateById(user);
        }
    }

    /**
     * 根据手机号生成初始化密码,手机号后六位
     *
     * @param mobile 手机号
     * @return 加密密码
     */
    private String initPassword(String mobile) {
        String rsaPassword = SecureUtil.sha256(mobile.substring(3));
        return encoder.encode(rsaPassword);
    }

    /**
     * 校验密码是否正确
     *
     * @param rawPassword    原始密码(用户输入的)
     * @param targetPassword 真实加密后的密码(数据库保存的)
     */
    private void checkPassword(String rawPassword, String targetPassword) {
        boolean match = encoder.match(rawPassword, targetPassword);
        if (!match) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }
    }

    /**
     * 根据用户名或手机号查询用户信息
     *
     * @param userName 用户名或电话号码
     * @return 用户信息
     */
    private SysUser getByAccount(String userName) {
        if (PhoneUtil.isMobile(userName)) {
            return this.getByMobile(userName);
        } else {
            return this.getByUserName(userName);
        }
    }

    /**
     * 根据手机号码查询用户信息
     *
     * @param mobile 手机号码
     * @return 用户信息
     */
    private SysUser getByMobile(String mobile) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getMobile, mobile);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 根据账户名查询用户信息
     *
     * @param userName 账户名
     * @return 用户信息
     */
    private SysUser getByUserName(String userName) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserName, userName);
        return sysUserMapper.selectOne(wrapper);
    }

    /**
     * 获取用户信息并校验密码登是否匹配
     *
     * @param userName userName
     * @param password password md5加密过
     * @return 用户信息
     */
    private SysUser getAndCheckUser(String userName, String password) {
        Integer present = LOGIN_LOCK_CACHE.getIfPresent(userName);
        if (present != null && present > MAX_ERROR_NUM) {
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
        SysUser user = this.getByAccount(userName);
        if (user == null || user.getState() == UserState.LOGOUT) {
            LOGIN_LOCK_CACHE.put(userName, present == null ? 1 : present + 1);
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        boolean match = encoder.match(SecureUtil.sha256(password), user.getPwd());
        if (!match) {
            LOGIN_LOCK_CACHE.put(userName, present == null ? 1 : present + 1);
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        if (user.getState() == UserState.LOCK) {
            throw new BusinessException(ErrorCode.USER_LOCKED_ERROR);
        }
        return user;
    }

    /**
     * 根据手机号查询用户信息并校验基本信息, 获取用户为空不增加错误次数,而是在发短信时校验次数
     *
     * @param mobile 手机号
     * @return 用户信息
     */
    private SysUser getAndCheckUser(String mobile) {
        Integer present = LOGIN_LOCK_CACHE.getIfPresent(mobile);
        if (present != null && present > MAX_ERROR_NUM) {
            throw new BusinessException(ErrorCode.USER_ERROR_LOCK);
        }
        SysUser user = this.getByMobile(mobile);
        if (user == null || user.getState() == UserState.LOGOUT) {
            throw new BusinessException(ErrorCode.USER_MOBILE_NULL);
        }
        if (user.getState() == UserState.LOCK) {
            throw new BusinessException(ErrorCode.USER_LOCKED_ERROR);
        }
        return user;
    }

    /**
     * 校验手机号是否重复
     *
     * @param mobile 手机号
     * @param id     id (更新时不能为空)
     */
    private void redoMobile(String mobile, Long id) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getMobile, mobile);
        wrapper.ne(id != null, SysUser::getId, id);
        Long count = sysUserMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("手机号码被占用 [{}] [{}]", mobile, id);
            throw new BusinessException(ErrorCode.MOBILE_REDO);
        }
    }

    /**
     * 校验账户名是否重复
     *
     * @param userName 账户名
     * @param id       id (更新时不能为空)
     */
    private void redoUserName(String userName, Long id) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getUserName, userName);
        wrapper.ne(id != null, SysUser::getId, id);
        Long count = sysUserMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("账户名被占用 [{}] [{}]", userName, id);
            throw new BusinessException(ErrorCode.USER_NAME_REDO);
        }
    }
}
