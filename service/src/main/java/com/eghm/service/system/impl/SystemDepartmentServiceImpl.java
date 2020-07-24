package com.eghm.service.system.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.mapper.system.SystemDepartmentMapper;
import com.eghm.dao.model.system.SystemDepartment;
import com.eghm.model.dto.system.department.DepartmentAddRequest;
import com.eghm.model.dto.system.department.DepartmentEditRequest;
import com.eghm.service.system.SystemDepartmentService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/12/13 16:49
 */
@Service("systemDepartmentService")
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j
public class SystemDepartmentServiceImpl implements SystemDepartmentService {

    private SystemDepartmentMapper systemDepartmentMapper;

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";

    /**
     * 根节点默认 0
     */
    private static final String ROOT = "0";

    @Autowired
    public void setSystemDepartmentMapper(SystemDepartmentMapper systemDepartmentMapper) {
        this.systemDepartmentMapper = systemDepartmentMapper;
    }

    @Override
    public SystemDepartment getById(Integer id) {
        return systemDepartmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDepartment> getDepartment() {
        return systemDepartmentMapper.getList();
    }

    @Override
    public void addDepartment(DepartmentAddRequest request) {
        SystemDepartment department = DataUtil.copy(request, SystemDepartment.class);
        String code = this.getNextCode(request.getParentCode());
        department.setCode(code);
        systemDepartmentMapper.insertSelective(department);
    }

    @Override
    public void editDepartment(DepartmentEditRequest request) {
        SystemDepartment department = DataUtil.copy(request, SystemDepartment.class);
        systemDepartmentMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    public String getNextCode(String code) {
        SystemDepartment child = systemDepartmentMapper.getMaxCodeChild(code);
        if (child == null) {
            return ROOT.equals(code) ? STEP :  code + STEP;
        }
        // 不校验子部门上限,傻子才会有900个部门
        try {
            return String.valueOf(Long.parseLong(child.getCode()) + 1);
        } catch (NumberFormatException e) {
            log.warn("部门编号生成失败 code:[{}]", code);
            throw new BusinessException(ErrorCode.DEPARTMENT_DEPTH_ERROR);
        }
    }

}
