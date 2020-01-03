package com.eghm.service.system.impl;

import com.eghm.common.utils.StringUtil;
import com.eghm.dao.mapper.system.SystemDepartmentMapper;
import com.eghm.dao.model.system.SystemDepartment;
import com.eghm.model.dto.system.department.DepartmentAddRequest;
import com.eghm.model.dto.system.department.DepartmentEditRequest;
import com.eghm.service.system.SystemDepartmentService;
import com.eghm.utils.DataUtil;
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
public class SystemDepartmentServiceImpl implements SystemDepartmentService {

    @Autowired
    private SystemDepartmentMapper systemDepartmentMapper;

    /**
     * 根节点或子节点的根节点编码
     */
    private static final String ROOT_CODE = "100";

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
        if (StringUtil.isBlank(code)) {
            return ROOT_CODE;
        }
        SystemDepartment child = systemDepartmentMapper.getMaxCodeChild(code);
        if (child == null) {
            return code + ROOT_CODE;
        }
        return String.valueOf(Long.parseLong(child.getCode()) + 1);
    }

}
