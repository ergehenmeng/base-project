package com.eghm.service.sys.impl;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.cache.CacheService;
import com.eghm.common.SmsService;
import com.eghm.common.UserTokenService;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constants.CacheConstant;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.sys.login.SmsLoginRequest;
import com.eghm.dto.sys.user.PasswordEditRequest;
import com.eghm.dto.sys.user.UserAddRequest;
import com.eghm.dto.sys.user.UserEditRequest;
import com.eghm.dto.sys.user.UserQueryRequest;
import com.eghm.enums.DataType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.TemplateType;
import com.eghm.enums.UserType;
import com.eghm.enums.ref.UserState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantMapper;
import com.eghm.mapper.MerchantUserMapper;
import com.eghm.mapper.SysUserMapper;
import com.eghm.model.SysDataDept;
import com.eghm.model.SysUser;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.menu.MenuResponse;
import com.eghm.vo.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CommonConstant.MAX_ERROR_NUM;
import static com.eghm.utils.CacheUtil.LOGIN_LOCK_CACHE;

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

    private final SysUserMapper sysUserMapper;

    private final SysRoleService sysRoleService;

    private final MerchantMapper merchantMapper;

    private final SysMenuService sysMenuService;

    private final UserTokenService userTokenService;

    private final SysDataDeptService sysDataDeptService;

    private final MerchantUserMapper merchantUserMapper;

    @Override
    public Page<UserResponse> getByPage(UserQueryRequest request) {
        request.setUserType(UserType.SYS_USER.getValue());
        return sysUserMapper.listPage(request.createPage(), request);
    }

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        SysUser user = sysUserMapper.selectById(request.getUserId());
        this.checkPassword(request.getOldPwd(), user.getPwd());
        String newPassword = encoder.encode(request.getNewPwd());
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
        String initPassword = this.initPassword(request.getMobile());
        user.setPwd(initPassword);
        user.setInitPwd(initPassword);
        user.setPwdUpdateTime(LocalDateTime.now());
        sysUserMapper.insert(user);
        // 角色权限
        sysRoleService.auth(user.getId(), request.getRoleIds());
        // 数据权限
        if (request.getDataType() == DataType.CUSTOM) {
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
        }
    }

    @Override
    public void insert(SysUser user) {
        this.redoUserName(user.getUserName(), null);
        this.redoMobile(user.getMobile(), null);
        user.setState(UserState.NORMAL);
        sysUserMapper.insert(user);
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
        if (request.getDataType() != null && request.getDataType().intValue() == DataType.CUSTOM.getValue()) {
            // 删除旧数据权限
            sysDataDeptService.deleteByUserId(user.getId());
            // 添加新数据权限
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
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
    }

    @Override
    public void resetPassword(Long id, String pwd) {
        LambdaUpdateWrapper<SysUser> wrapper = Wrappers.lambdaUpdate();
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        wrapper.eq(SysUser::getId, id);
        wrapper.set(SysUser::getPwd, encode);
        wrapper.set(SysUser::getInitPwd, encode);
        wrapper.set(SysUser::getPwdUpdateTime, LocalDateTime.now());
        sysUserMapper.update(null, wrapper);
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
    public LoginResponse login(String userName, String password) {
        SysUser user = this.getAndCheckUser(userName, password);
        return this.doLogin(user);
    }

    @Override
    public void sendLoginSms(String mobile, String ip) {
        SysUser user = this.getAndCheckUser(mobile);
        smsService.sendSmsCode(TemplateType.USER_LOGIN, user.getMobile(), ip);
    }

    @Override
    public LoginResponse smsLogin(SmsLoginRequest request) {
        smsService.verifySmsCode(TemplateType.USER_LOGIN, request.getMobile(), request.getSmsCode());
        SysUser user = this.getAndCheckUser(request.getMobile());
        return this.doLogin(user);
    }

    @Override
    public void updateById(SysUser user) {
        this.redoUserName(user.getUserName(), user.getId());
        this.redoMobile(user.getMobile(), user.getId());
        sysUserMapper.updateById(user);
    }

    /**
     * 根据手机号生成初始化密码,手机号后六位
     *
     * @param mobile 手机号
     * @return 加密密码
     */
    private String initPassword(String mobile) {
        String md5Password = MD5.create().digestHex(mobile.substring(3));
        return encoder.encode(md5Password);
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
     * 管理后台登陆
     *
     * @param user 用户信息
     * @return 返回前端信息
     */
    private LoginResponse doLogin(SysUser user) {
        UserType userType = user.getUserType();
        // 如果用户拥有超管角色,则默认查询全部菜单等信息
        List<MenuResponse> leftMenu;
        List<String> buttonList;
        if (userType == UserType.ADMINISTRATOR) {
            leftMenu = sysMenuService.getAdminLeftMenuList();
            buttonList = sysMenuService.getAdminPermCode();
        } else {
            buttonList = sysMenuService.getPermCode(user.getId());
            leftMenu = sysMenuService.getLeftMenuList(user.getId());
        }

        int merchantType = 0;
        if (userType == UserType.MERCHANT_ADMIN) {
            merchantType = sysUserMapper.getMerchantType(user.getId());
        } else if (userType == UserType.MERCHANT_USER) {
            merchantType = merchantUserMapper.getMerchantType(user.getId());
        }

        // 数据权限(此处没有判断,逻辑不够严谨,仅仅为了代码简洁)
        List<String> customList = sysDataDeptService.getDeptList(user.getId());
        Long merchantId = this.getMerchantId(user.getId(), user.getUserType());

        String token = userTokenService.createToken(user, merchantId, buttonList, customList);

        LoginResponse response = new LoginResponse();
        response.setMerchantId(merchantId);
        response.setToken(token);
        response.setNickName(user.getNickName());
        response.setPermList(buttonList);
        response.setUserType(user.getUserType());
        response.setMenuList(leftMenu);
        response.setMerchantType(merchantType);
        response.setInit(user.getInitPwd().equals(user.getPwd()));
        response.setExpire(user.getPwdUpdateTime().plusDays(CommonConstant.PWD_UPDATE_TIPS).isBefore(LocalDateTime.now()));
        cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
        LOGIN_LOCK_CACHE.invalidate(user.getMobile());
        return response;
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
        boolean match = encoder.match(password, user.getPwd());
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
     * 根据手机号查询用户信息并校验基本信息
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
     * 查询用户关联的商户id, 只针对普通商户
     *
     * @param userId   userId
     * @param userType 用户类型
     * @return merchantId
     */
    private Long getMerchantId(Long userId, UserType userType) {
        Long merchantId = null;
        if (userType == UserType.MERCHANT_ADMIN) {
            merchantId = merchantMapper.getByUserId(userId);
            if (merchantId == null) {
                log.error("商户信息未查询到 [{}]", userId);
                throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
            }
        } else if (userType == UserType.MERCHANT_USER) {
            merchantId = merchantUserMapper.getByUserId(userId);
            if (merchantId == null) {
                log.error("商户普通用户信息未查询到 [{}]", userId);
                throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
            }
        }
        return merchantId;
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
     * @param id     id (更新时不能为空)
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
