package com.eghm.service.sys.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.DataType;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.mapper.SysOperatorMapper;
import com.eghm.model.SysDataDept;
import com.eghm.model.SysOperator;
import com.eghm.model.dto.operator.OperatorAddRequest;
import com.eghm.model.dto.operator.OperatorEditRequest;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.model.dto.operator.PasswordEditRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.model.vo.menu.MenuResponse;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
@Service("sysOperatorService")
@AllArgsConstructor
@Slf4j
public class SysOperatorServiceImpl implements SysOperatorService {

    private final SysOperatorMapper sysOperatorMapper;

    private final Encoder encoder;

    private final SysRoleService sysRoleService;

    private final SysDataDeptService sysDataDeptService;

    private final SysMenuService sysMenuService;

    private final JwtTokenService jwtTokenService;

    @Override
    public SysOperator getByMobile(String mobile) {
        LambdaQueryWrapper<SysOperator> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOperator::getMobile, mobile);
        return sysOperatorMapper.selectOne(wrapper);
    }

    @Override
    public void updateLoginPassword(PasswordEditRequest request) {
        SysOperator operator = sysOperatorMapper.selectById(request.getOperatorId());
        this.checkPassword(request.getOldPwd(), operator.getPwd());
        String newPassword = encoder.encode(request.getNewPwd());
        operator.setPwd(newPassword);
        sysOperatorMapper.updateById(operator);
    }

    @Override
    public void checkPassword(String rawPassword, String targetPassword) {
        String oldPassword = encoder.encode(rawPassword);
        if (!targetPassword.equals(oldPassword)) {
            throw new BusinessException(ErrorCode.OPERATOR_PASSWORD_ERROR);
        }
    }

    @Override
    public Page<SysOperator> getByPage(OperatorQueryRequest request) {
        LambdaQueryWrapper<SysOperator> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, SysOperator::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysOperator::getNickName, request.getQueryName()).or().
                        like(SysOperator::getMobile, request.getQueryName()));
        return sysOperatorMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(OperatorAddRequest request) {
        this.redoMobile(request.getMobile(), null);
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        operator.setState(SysOperator.STATE_1);
        operator.setUserType(SysOperator.USER_TYPE_1);
        String initPassword = this.initPassword(request.getMobile());
        operator.setPwd(initPassword);
        operator.setInitPwd(initPassword);
        sysOperatorMapper.insert(operator);
        // 角色权限
        sysRoleService.authRole(operator.getId(), request.getRoleIds());
        // 数据权限
        if (request.getPermissionType() == DataType.CUSTOM.getValue()) {
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(operator.getId(), s)));
        }
    }

    @Override
    public void insert(SysOperator operator) {
        this.redoMobile(operator.getMobile(), null);
        operator.setState(SysOperator.STATE_1);
        sysOperatorMapper.insert(operator);
    }

    @Override
    public String initPassword(String mobile) {
        String md5Password = MD5.create().digestHex(mobile.substring(5));
        return encoder.encode(md5Password);
    }

    @Override
    public SysOperator getById(Long id) {
        return sysOperatorMapper.selectById(id);
    }

    @Override
    public void update(OperatorEditRequest request) {
        this.redoMobile(request.getMobile(), request.getId());

        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        sysOperatorMapper.updateById(operator);
        // 数据权限
        if (request.getPermissionType() == DataType.CUSTOM.getValue()) {
            sysDataDeptService.deleteByOperatorId(operator.getId());
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(operator.getId(), s)));
        }
    }

    @Override
    public void resetPassword(Long id) {
        SysOperator operator = this.getById(id);
        String password = this.initPassword(operator.getMobile());
        operator.setPwd(password);
        operator.setInitPwd(password);
        sysOperatorMapper.updateById(operator);
    }

    @Override
    public void resetPassword(Long id, String pwd) {
        LambdaUpdateWrapper<SysOperator> wrapper = Wrappers.lambdaUpdate();
        String encode = encoder.encode(MD5.create().digestHex(pwd));
        wrapper.set(SysOperator::getPwd, encode);
        wrapper.set(SysOperator::getInitPwd, encode);
        wrapper.eq(SysOperator::getId, id);
        sysOperatorMapper.update(null, wrapper);
    }

    @Override
    public void delete(Long id) {
        sysOperatorMapper.deleteById(id);
    }

    @Override
    public void lockOperator(Long id) {
        SysOperator operator = new SysOperator();
        operator.setId(id);
        operator.setState(SysOperator.STATE_0);
        sysOperatorMapper.updateById(operator);
    }

    @Override
    public void unlockOperator(Long id) {
        SysOperator operator = new SysOperator();
        operator.setId(id);
        operator.setState(SysOperator.STATE_1);
        sysOperatorMapper.updateById(operator);
    }

    @Override
    public LoginResponse login(String userName, String password) {
        SysOperator operator = this.getByMobile(userName);
        if (operator == null) {
            throw new BusinessException(ErrorCode.OPERATOR_NOT_FOUND);
        }
        if (operator.getState() == 0) {
            throw new BusinessException(ErrorCode.OPERATOR_LOCKED_ERROR);
        }
        boolean match = encoder.match(password, operator.getPwd());
        if (!match) {
            throw new BusinessException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        boolean adminRole = sysRoleService.isAdminRole(operator.getId());
        List<String> buttonList;
        // 如果用户拥有超管角色,则默认查询全部菜单等信息
        List<MenuResponse> leftMenu;
        if (adminRole) {
            leftMenu = sysMenuService.getLeftMenuList();
            buttonList = sysMenuService.getAuth();
        } else {
            buttonList = sysMenuService.getAuth(operator.getId());
            leftMenu = sysMenuService.getLeftMenuList(operator.getId());
        }
        // 根据用户名和权限创建jwtToken
        LoginResponse response = new LoginResponse();
        response.setToken(jwtTokenService.createToken(operator, buttonList));
        response.setButtonList(buttonList);
        response.setUserType(operator.getUserType());
        response.setLeftMenuList(leftMenu);
        return response;
    }

    /**
     * 校验手机号是否重复
     * @param mobile 手机号
     * @param id id (更新时不能为空)
     */
    private void redoMobile(String mobile, Long id) {
        LambdaQueryWrapper<SysOperator> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOperator::getMobile, mobile);
        wrapper.ne(id != null, SysOperator::getId, id);
        Integer count = sysOperatorMapper.selectCount(wrapper);
        if (count > 0) {
            log.warn("手机号码被占用 [{}] [{}]", mobile, id);
            throw new BusinessException(ErrorCode.MOBILE_REDO);
        }
    }
}
