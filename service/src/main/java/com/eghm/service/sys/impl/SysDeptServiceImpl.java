package com.eghm.service.sys.impl;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.SysDeptMapper;
import com.eghm.model.SysDept;
import com.eghm.model.dto.dept.DeptAddRequest;
import com.eghm.model.dto.dept.DeptEditRequest;
import com.eghm.service.sys.SysDeptService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门 service
 * @author 二哥很猛
 * @date 2018/12/13 16:49
 */
@Service("sysDeptService")
@Slf4j
@AllArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";

    /**
     * 根节点默认 0
     */
    private static final String ROOT = "0";

    @Override
    public SysDept getById(Long id) {
        return sysDeptMapper.selectById(id);
    }

    @Override
    public List<SysDept> getList() {
        return sysDeptMapper.selectList(null);
    }

    @Override
    public void create(DeptAddRequest request) {
        SysDept department = DataUtil.copy(request, SysDept.class);
        String code = this.getNextCode(request.getParentCode());
        department.setCode(code);
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        department.setOperatorId(operator.getId());
        department.setOperatorName(operator.getOperatorName());
        sysDeptMapper.insert(department);
    }

    @Override
    public void update(DeptEditRequest request) {
        SysDept department = DataUtil.copy(request, SysDept.class);
        sysDeptMapper.updateById(department);
    }

    /**
     * 根据列表计算出子级部门下一个编码的值
     * 初始编号默认101,后面依次累计+1
     * @param code 部门编号
     * @return 下一个编号
     */
    private String getNextCode(String code) {
        String maxCode = sysDeptMapper.getMaxCodeChild(code);
        if (maxCode == null) {
            return ROOT.equals(code) ? STEP :  code + STEP;
        }
        // 不校验子部门上限,傻子才会有900个部门
        try {
            return String.valueOf(Long.parseLong(maxCode) + 1);
        } catch (NumberFormatException e) {
            log.warn("部门编号生成失败 code:[{}]", code);
            throw new BusinessException(ErrorCode.DEPARTMENT_DEPTH_ERROR);
        }
    }
}
