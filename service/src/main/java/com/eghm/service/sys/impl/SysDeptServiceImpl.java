package com.eghm.service.sys.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.mapper.SysDeptMapper;
import com.eghm.dao.model.SysDept;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.model.dto.dept.DeptEditRequest;
import com.eghm.service.sys.SysDeptService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 部门 service
 * @author 二哥很猛
 * @date 2018/12/13 16:49
 */
@Service("sysDeptService")
@Transactional(rollbackFor = RuntimeException.class)
@Slf4j
public class SysDeptServiceImpl implements SysDeptService {

    private SysDeptMapper sysDeptMapper;

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";

    /**
     * 根节点默认 0
     */
    private static final String ROOT = "0";

    @Autowired
    public void setSysDeptMapper(SysDeptMapper sysDeptMapper) {
        this.sysDeptMapper = sysDeptMapper;
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public SysDept getById(Integer id) {
        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public List<SysDept> getDepartment() {
        return sysDeptMapper.getList();
    }

    @Override
    public void addDepartment(DeptAddRequest request) {
        SysDept department = DataUtil.copy(request, SysDept.class);
        String code = this.getNextCode(request.getParentCode());
        department.setCode(code);
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        department.setOperatorId(operator.getId());
        department.setOperatorName(operator.getOperatorName());
        sysDeptMapper.insertSelective(department);
    }

    @Override
    public void editDepartment(DeptEditRequest request) {
        SysDept department = DataUtil.copy(request, SysDept.class);
        sysDeptMapper.updateByPrimaryKeySelective(department);
    }

    @Override
    @Transactional(readOnly = true, rollbackFor = RuntimeException.class)
    public String getNextCode(String code) {
        SysDept child = sysDeptMapper.getMaxCodeChild(code);
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
