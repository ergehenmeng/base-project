package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.constant.CacheConstant;
import com.eghm.dto.user.PasswordEditRequest;
import com.eghm.dto.user.UserAddRequest;
import com.eghm.dto.user.UserEditRequest;
import com.eghm.dto.user.UserQueryRequest;
import com.eghm.enums.DataType;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MerchantMapper;
import com.eghm.mapper.MerchantUserMapper;
import com.eghm.mapper.SysUserMapper;
import com.eghm.model.SysDataDept;
import com.eghm.model.SysUser;
import com.eghm.service.business.MerchantUserService;
import com.eghm.service.cache.CacheService;
import com.eghm.service.common.AccessTokenService;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.service.sys.SysUserService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.login.LoginResponse;
import com.eghm.vo.menu.MenuResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
@Service("sysUserService")
@AllArgsConstructor
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final MerchantMapper merchantMapper;

    private final MerchantUserMapper merchantUserMapper;

    private final Encoder encoder;

    private final SysRoleService sysRoleService;

    private final SysDataDeptService sysDataDeptService;

    private final SysMenuService sysMenuService;

    private final AccessTokenService accessTokenService;

    private final CacheService cacheService;

    @Override
    public Page<SysUser> getByPage(UserQueryRequest request) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        // 只查询系统用户
        wrapper.eq(SysUser::getUserType, SysUser.USER_TYPE_1);
        wrapper.eq(request.getState() != null, SysUser::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysUser::getNickName, request.getQueryName()).or().
                        like(SysUser::getMobile, request.getQueryName()));
        return sysUserMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysUser getByMobile(String mobile) {
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysUser::getMobile, mobile);
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        SysUser user = sysUserMapper.selectById(request.getUserId());
        this.checkPassword(request.getOldPwd(), user.getPwd());
        String newPassword = encoder.encode(request.getNewPwd());
        user.setPwd(newPassword);
        sysUserMapper.updateById(user);
    }

    @Override
    public void checkPassword(String rawPassword, String targetPassword) {
        boolean match = encoder.match(rawPassword, targetPassword);
        if (!match) {
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }
    }

    @Override
    public void checkPassword(Long userId, String rawPassword) {
        SysUser user = sysUserMapper.selectById(userId);
        boolean match = encoder.match(rawPassword, user.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
    }

    @Override
    public void create(UserAddRequest request) {
        this.redoMobile(request.getMobile(), null);
        SysUser user = DataUtil.copy(request, SysUser.class);
        user.setState(SysUser.STATE_1);
        user.setUserType(SysUser.USER_TYPE_1);
        String initPassword = this.initPassword(request.getMobile());
        user.setPwd(initPassword);
        user.setInitPwd(initPassword);
        sysUserMapper.insert(user);
        // 角色权限
        sysRoleService.authRole(user.getId(), request.getRoleIds());
        // 数据权限
        if (request.getDataType() == DataType.CUSTOM) {
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
        }
    }

    @Override
    public void insert(SysUser user) {
        this.redoMobile(user.getMobile(), null);
        user.setState(SysUser.STATE_1);
        sysUserMapper.insert(user);
    }

    @Override
    public String initPassword(String mobile) {
        String md5Password = MD5.create().digestHex(mobile.substring(5));
        return encoder.encode(md5Password);
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectById(id);
    }

    @Override
    public void update(UserEditRequest request) {
        this.redoMobile(request.getMobile(), request.getId());

        SysUser user = DataUtil.copy(request, SysUser.class);
        sysUserMapper.updateById(user);
        // 数据权限, 在新增系统用户时,可以手动指定数据权限,此处既是将用户与其所拥有的的部门权限做关联,方便后续进行数据权限分组
        if (request.getDataType() != null && request.getDataType() == DataType.CUSTOM.getValue()) {
            // 删除旧数据权限
            sysDataDeptService.deleteByUserId(user.getId());
            // 添加新数据权限
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(user.getId(), s)));
        }
    }

    @Override
    public void resetPassword(Long id) {
        SysUser user = this.getById(id);
        String password = this.initPassword(user.getMobile());
        user.setPwd(password);
        user.setInitPwd(password);
        sysUserMapper.updateById(user);
    }

    @Override
    public void resetPassword(Long id, String pwd) {
        LambdaUpdateWrapper<SysUser> wrapper = Wrappers.lambdaUpdate();
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        wrapper.set(SysUser::getPwd, encode);
        wrapper.set(SysUser::getInitPwd, encode);
        wrapper.eq(SysUser::getId, id);
        sysUserMapper.update(null, wrapper);
    }

    @Override
    public void deleteById(Long id) {
        sysUserMapper.deleteById(id);
    }

    @Override
    public void lockUser(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setState(SysUser.STATE_0);
        sysUserMapper.updateById(user);
    }

    @Override
    public void unlockUser(Long id) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setState(SysUser.STATE_1);
        sysUserMapper.updateById(user);
    }

    @Override
    public LoginResponse login(String userName, String password) {
        SysUser user = this.getByMobile(userName);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        if (user.getState() == 0) {
            throw new BusinessException(ErrorCode.USER_LOCKED_ERROR);
        }
        boolean match = encoder.match(password, user.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        boolean adminRole = sysRoleService.isAdminRole(user.getId());
        List<String> buttonList;
        // 如果用户拥有超管角色,则默认查询全部菜单等信息
        List<MenuResponse> leftMenu;
        if (adminRole) {
            leftMenu = sysMenuService.getAdminLeftMenuList();
            buttonList = sysMenuService.getAdminPermCode();
        } else {
            buttonList = sysMenuService.getPermCode(user.getId());
            leftMenu = sysMenuService.getLeftMenuList(user.getId());
        }
        // 根据用户名和权限创建jwtToken
        LoginResponse response = new LoginResponse();

        // 数据权限(此处没有判断,逻辑不够严谨,仅仅为了代码简洁)
        List<String> customList = sysDataDeptService.getDeptList(user.getId());

        String token = accessTokenService.createToken(user, this.getMerchantId(user.getId(), user.getUserType()), buttonList, customList);

        response.setToken(token);
        response.setButtonList(buttonList);
        response.setUserType(user.getUserType());
        response.setLeftMenuList(leftMenu);
        cacheService.delete(CacheConstant.LOCK_SCREEN + user.getId());
        return response;
    }

    @Override
    public void updateById(SysUser user) {
        this.redoMobile(user.getMobile(), user.getId());
        sysUserMapper.updateById(user);
    }

    /**
     * 查询用户关联的商户id, 只针对普通商户
     * @param userId userId
     * @param userType 用户类型
     * @return merchantId
     */
    private Long getMerchantId(Long userId, Integer userType) {
        Long merchantId = null;
        if (userType == SysUser.USER_TYPE_2) {
            merchantId = merchantMapper.getByUserId(userId);
            if (merchantId == null) {
                log.error("商户信息未查询到 [{}]", userId);
                throw new BusinessException(ErrorCode.MERCHANT_NOT_FOUND);
            }
        } else if (userType == SysUser.USER_TYPE_3) {
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
     * @param mobile 手机号
     * @param id id (更新时不能为空)
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
}
