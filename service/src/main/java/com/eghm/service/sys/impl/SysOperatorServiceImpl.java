package com.eghm.service.sys.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.PermissionType;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.encoder.Encoder;
import com.eghm.dao.mapper.SysOperatorMapper;
import com.eghm.dao.model.SysDataDept;
import com.eghm.dao.model.SysOperator;
import com.eghm.model.dto.operator.OperatorAddRequest;
import com.eghm.model.dto.operator.OperatorEditRequest;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.model.dto.operator.PasswordEditRequest;
import com.eghm.model.vo.login.LoginResponse;
import com.eghm.service.common.JwtTokenService;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysMenuService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
@Service("sysOperatorService")
@AllArgsConstructor
public class SysOperatorServiceImpl implements SysOperatorService {

    private final SysOperatorMapper sysOperatorMapper;

    private final Encoder encoder;

    private final SysRoleService sysRoleService;

    private final SysDataDeptService sysDataDeptService;

    private final SysMenuService sysMenuService;

    private final JwtTokenService jwtTokenService;

    @Override
    public SysOperator getByMobile(String mobile) {
        return sysOperatorMapper.getByMobile(mobile);
    }

    @Override
    public String updateLoginPassword(PasswordEditRequest request) {
        SysOperator operator = sysOperatorMapper.selectById(request.getOperatorId());
        this.checkPassword(request.getOldPwd(), operator.getPwd());
        String newPassword = encoder.encode(request.getNewPwd());
        operator.setPwd(newPassword);
        sysOperatorMapper.updateById(operator);
        return newPassword;
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
                queryWrapper.like(SysOperator::getOperatorName, request.getQueryName()).or().
                        like(SysOperator::getMobile, request.getQueryName()));
        return sysOperatorMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(OperatorAddRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        operator.setState(SysOperator.STATE_1);
        String initPassword = this.initPassword(request.getMobile());
        operator.setPwd(initPassword);
        operator.setInitPwd(initPassword);
        sysOperatorMapper.insert(operator);
        // 角色权限
        sysRoleService.authRole(operator.getId(), request.getRoleIds());
        // 数据权限
        if (request.getPermissionType() == PermissionType.CUSTOM.getValue()) {
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(operator.getId(), s)));
        }
    }


    @Override
    public String initPassword(String mobile) {
        String md5Password = MD5.create().digestHex(Base64.encode(mobile.substring(5)));
        return encoder.encode(md5Password);
    }

    @Override
    public SysOperator getById(Long id) {
        return sysOperatorMapper.selectById(id);
    }

    @Override
    public void update(OperatorEditRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        sysOperatorMapper.updateById(operator);

        // 数据权限
        if (request.getPermissionType() == PermissionType.CUSTOM.getValue()) {
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
        SysOperator operator = sysOperatorMapper.getByMobile(userName);
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
        // 根据用户名和权限创建jwtToken
        List<String> authorities = sysMenuService.getAuthByOperatorId(operator.getId());
        LoginResponse response = new LoginResponse();
        response.setToken(jwtTokenService.createToken(operator, authorities));
        response.setRefreshToken(jwtTokenService.createRefreshToken(operator));
        response.setAuthorityList(authorities);
        return response;
    }
}
