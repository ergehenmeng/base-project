package com.eghm.service.system.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.Md5Util;
import com.eghm.common.utils.StringUtil;
import com.eghm.configuration.security.Encoder;
import com.eghm.dao.mapper.system.SystemOperatorMapper;
import com.eghm.dao.mapper.system.SystemOperatorRoleMapper;
import com.eghm.dao.model.system.SystemOperator;
import com.eghm.dao.model.system.SystemOperatorRole;
import com.eghm.model.dto.system.operator.OperatorAddRequest;
import com.eghm.model.dto.system.operator.OperatorEditRequest;
import com.eghm.model.dto.system.operator.OperatorQueryRequest;
import com.eghm.model.dto.system.operator.PasswordEditRequest;
import com.eghm.service.system.SystemOperatorService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2018/11/26 10:24
 */
@Service("systemOperatorService")
@Transactional(rollbackFor = RuntimeException.class)
public class SystemOperatorServiceImpl implements SystemOperatorService {

    @Autowired
    private SystemOperatorMapper systemOperatorMapper;

    @Autowired
    private Encoder encoder;

    @Autowired
    private SystemOperatorRoleMapper systemOperatorRoleMapper;

    @Override
    public SystemOperator getByMobile(String mobile) {
        return systemOperatorMapper.getByMobile(mobile);
    }

    @Override
    public String updateLoginPassword(PasswordEditRequest request) {
        SystemOperator operator = systemOperatorMapper.selectByPrimaryKey(request.getOperatorId());
        String oldPassword = encoder.encode(request.getOldPwd());
        if (!operator.getPwd().equals(oldPassword)) {
            throw new BusinessException(ErrorCode.OPERATOR_PASSWORD_ERROR);
        }
        String newPassword = encoder.encode(request.getNewPwd());
        operator.setPwd(newPassword);
        systemOperatorMapper.updateByPrimaryKeySelective(operator);
        return newPassword;
    }

    @Override
    public PageInfo<SystemOperator> getByPage(OperatorQueryRequest request) {
        PageHelper.startPage(request.getPage(), request.getPageSize());
        List<SystemOperator> list = systemOperatorMapper.getList(request);
        return new PageInfo<>(list);
    }

    @Override
    public void addOperator(OperatorAddRequest request) {
        SystemOperator operator = DataUtil.copy(request, SystemOperator.class);
        operator.setDeleted(false);
        operator.setState(1);
        String initPassword = this.initPassword(request.getMobile());
        operator.setPwd(initPassword);
        operator.setInitPwd(initPassword);
        systemOperatorMapper.insertSelective(operator);
        if (StringUtil.isNotBlank(request.getRoleIds())) {
            List<String> roleStringList = Splitter.on(",").splitToList(request.getRoleIds());
            //循环插入角色关联信息
            roleStringList.forEach(s -> systemOperatorRoleMapper.insertSelective(new SystemOperatorRole(operator.getId(), Integer.parseInt(s))));
        }
    }


    @Override
    public String initPassword(String mobile) {
        String md5Password = Md5Util.md5(mobile.substring(5));
        return encoder.encode(md5Password);
    }

    @Override
    public SystemOperator getById(Integer id) {
        return systemOperatorMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateOperator(OperatorEditRequest request) {
        SystemOperator operator = DataUtil.copy(request, SystemOperator.class);
        systemOperatorMapper.updateByPrimaryKeySelective(operator);
        systemOperatorRoleMapper.deleteByOperatorId(request.getId());
        if (StringUtil.isNotBlank(request.getRoleIds())) {
            List<String> stringList = Splitter.on(",").splitToList(request.getRoleIds());
            List<Integer> roleList = stringList.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            systemOperatorRoleMapper.batchInsertOperatorRole(request.getId(), roleList);
        }
    }

    @Override
    public void resetPassword(Integer id) {
        SystemOperator operator = this.getById(id);
        String password = this.initPassword(operator.getMobile());
        operator.setPwd(password);
        operator.setInitPwd(password);
        systemOperatorMapper.updateByPrimaryKeySelective(operator);
    }
}
