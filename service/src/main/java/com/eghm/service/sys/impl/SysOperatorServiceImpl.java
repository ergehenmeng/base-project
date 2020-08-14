package com.eghm.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.Encoder;
import com.eghm.dao.mapper.sys.SysOperatorMapper;
import com.eghm.dao.mapper.sys.SysOperatorRoleMapper;
import com.eghm.dao.model.sys.SysOperator;
import com.eghm.dao.model.sys.SysOperatorRole;
import com.eghm.model.dto.sys.operator.OperatorAddRequest;
import com.eghm.model.dto.sys.operator.OperatorEditRequest;
import com.eghm.model.dto.sys.operator.OperatorQueryRequest;
import com.eghm.model.dto.sys.operator.PasswordEditRequest;
import com.eghm.service.sys.SysOperatorService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
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
@Transactional(rollbackFor = RuntimeException.class)
public class SysOperatorServiceImpl implements SysOperatorService {

    private SysOperatorMapper sysOperatorMapper;

    private Encoder encoder;

    private SysOperatorRoleMapper sysOperatorRoleMapper;

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

    @Override
    public SysOperator getByMobile(String mobile) {
        return sysOperatorMapper.getByMobile(mobile);
    }

    @Override
    public String updateLoginPassword(PasswordEditRequest request) {
        SysOperator operator = sysOperatorMapper.selectByPrimaryKey(request.getOperatorId());
        checkPassword(request.getOldPwd(), operator.getPwd());
        String newPassword = encoder.encode(request.getNewPwd());
        operator.setPwd(newPassword);
        sysOperatorMapper.updateByPrimaryKeySelective(operator);
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
    public PageInfo<SysOperator> getByPage(OperatorQueryRequest request) {
        PageMethod.startPage(request.getPage(), request.getPageSize());
        List<SysOperator> list = sysOperatorMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addOperator(OperatorAddRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        operator.setDeleted(false);
        operator.setState(1);
        String initPassword = this.initPassword(request.getMobile());
        operator.setPwd(initPassword);
        operator.setInitPwd(initPassword);
        sysOperatorMapper.insertSelective(operator);
        if (StrUtil.isNotBlank(request.getRoleIds())) {
            List<String> roleStringList = StrUtil.split(request.getRoleIds(), ',');
            //循环插入角色关联信息
            roleStringList.forEach(s -> sysOperatorRoleMapper.insertSelective(new SysOperatorRole(operator.getId(), Integer.parseInt(s))));
        }
    }


    @Override
    public String initPassword(String mobile) {
        String md5Password = MD5.create().digestHex(mobile.substring(5));
        return encoder.encode(md5Password);
    }

    @Override
    public SysOperator getById(Integer id) {
        return sysOperatorMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateOperator(OperatorEditRequest request) {
        SysOperator operator = DataUtil.copy(request, SysOperator.class);
        sysOperatorMapper.updateByPrimaryKeySelective(operator);
        sysOperatorRoleMapper.deleteByOperatorId(request.getId());
        if (StrUtil.isNotBlank(request.getRoleIds())) {
            List<String> stringList = StrUtil.split(request.getRoleIds(), ',');
            List<Integer> roleList = stringList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            sysOperatorRoleMapper.batchInsertOperatorRole(request.getId(), roleList);
        }
    }

    @Override
    public void resetPassword(Integer id) {
        SysOperator operator = this.getById(id);
        String password = this.initPassword(operator.getMobile());
        operator.setPwd(password);
        operator.setInitPwd(password);
        sysOperatorMapper.updateByPrimaryKeySelective(operator);
    }
}
