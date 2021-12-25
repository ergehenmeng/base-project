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
import com.eghm.dao.mapper.SysOperatorRoleMapper;
import com.eghm.dao.model.SysDataDept;
import com.eghm.dao.model.SysOperator;
import com.eghm.dao.model.SysOperatorRole;
import com.eghm.model.dto.operator.OperatorAddRequest;
import com.eghm.model.dto.operator.OperatorEditRequest;
import com.eghm.model.dto.operator.OperatorQueryRequest;
import com.eghm.model.dto.operator.PasswordEditRequest;
import com.eghm.service.sys.SysDataDeptService;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.utils.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
@Service("sysOperatorService")
public class SysOperatorServiceImpl implements SysOperatorService {

    private SysOperatorMapper sysOperatorMapper;

    private Encoder encoder;

    private SysOperatorRoleMapper sysOperatorRoleMapper;

    private SysDataDeptService sysDataDeptService;

    @Autowired
    public void setSysOperatorMapper(SysOperatorMapper sysOperatorMapper) {
        this.sysOperatorMapper = sysOperatorMapper;
    }

    @Autowired
    public void setEncoder(Encoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setSysOperatorRoleMapper(SysOperatorRoleMapper sysOperatorRoleMapper) {
        this.sysOperatorRoleMapper = sysOperatorRoleMapper;
    }

    @Autowired
    public void setSysDataDeptService(SysDataDeptService sysDataDeptService) {
        this.sysDataDeptService = sysDataDeptService;
    }

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
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public Page<SysOperator> getByPage(OperatorQueryRequest request) {
        LambdaQueryWrapper<SysOperator> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysOperator::getDeleted, false);
        wrapper.eq(request.getState() != null, SysOperator::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysOperator::getOperatorName, request.getQueryName()).or().
                        like(SysOperator::getMobile, request.getQueryName()));
        return sysOperatorMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void addOperator(OperatorAddRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        operator.setDeleted(false);
        operator.setState(SysOperator.STATE_1);
        String initPassword = this.initPassword(request.getMobile());
        operator.setPwd(initPassword);
        operator.setInitPwd(initPassword);
        sysOperatorMapper.insert(operator);
        if (StrUtil.isNotBlank(request.getRoleIds())) {
            List<String> roleStringList = StrUtil.split(request.getRoleIds(), ',');
            //循环插入角色关联信息
            roleStringList.forEach(s -> sysOperatorRoleMapper.insert(new SysOperatorRole(operator.getId(), Long.parseLong(s))));
        }
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
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateOperator(OperatorEditRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        sysOperatorMapper.updateById(operator);
        sysOperatorRoleMapper.deleteByOperatorId(request.getId());
        if (StrUtil.isNotBlank(request.getRoleIds())) {
            List<String> stringList = StrUtil.split(request.getRoleIds(), ',');
            List<Long> roleList = stringList.stream().map(Long::parseLong).collect(Collectors.toList());
            sysOperatorRoleMapper.batchInsertOperatorRole(request.getId(), roleList);
        }
        // 数据权限
        if (request.getPermissionType() == PermissionType.CUSTOM.getValue()) {
            sysDataDeptService.deleteByOperatorId(operator.getId());
            List<String> roleStringList = StrUtil.split(request.getDeptIds(), ',');
            roleStringList.forEach(s -> sysDataDeptService.insert(new SysDataDept(operator.getId(), s)));
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetPassword(Long id) {
        SysOperator operator = this.getById(id);
        String password = this.initPassword(operator.getMobile());
        operator.setPwd(password);
        operator.setInitPwd(password);
        sysOperatorMapper.updateById(operator);
    }

    @Override
    public void deleteOperator(Long id) {
        SysOperator operator = new SysOperator();
        operator.setId(id);
        operator.setDeleted(true);
        sysOperatorMapper.updateById(operator);
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
}
