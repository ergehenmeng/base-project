package com.eghm.service.sys.impl;

import com.eghm.common.CommonService;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.UserToken;
import com.eghm.dto.sys.dept.DeptAddRequest;
import com.eghm.dto.sys.dept.DeptEditRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.mapper.SysDeptMapper;
import com.eghm.model.SysDept;
import com.eghm.service.sys.SysDeptService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.TreeUtil;
import com.eghm.utils.ValidationUtil;
import com.eghm.vo.sys.ext.SysDeptResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.constants.CommonConstant.ROOT_NODE;
import static com.eghm.constants.CommonConstant.STEP_100;

/**
 * 部门 service
 *
 * @author 二哥很猛
 * @since 2018/12/13 16:49
 */
@Slf4j
@AllArgsConstructor
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;

    private final CommonService commonService;

    @Override
    public List<SysDeptResponse> getList(PagingQuery query) {
        List<SysDeptResponse> responseList = sysDeptMapper.getList(query.getQueryName());
        return TreeUtil.tree(responseList, ROOT_NODE, SysDeptResponse::getCode, SysDeptResponse::getParentCode, SysDeptResponse::setChildren);
    }

    @Override
    public void create(DeptAddRequest request) {
        ValidationUtil.redoCheck(sysDeptMapper, SysDept::getTitle, request.getTitle(), wrapper -> wrapper.eq(SysDept::getParentCode, request.getParentCode()), null, null, ErrorCode.DEPARTMENT_TITLE_REPEAT, "部门名称重复 [{}] [{}]");
        SysDept department = DataUtil.copy(request, SysDept.class);
        String code = this.getNextCode(request.getParentCode());
        department.setCode(code);
        UserToken user = SecurityHolder.getUserRequired();
        department.setUserId(user.getId());
        department.setUserName(user.getNickName());
        sysDeptMapper.insert(department);
    }

    @Override
    public void update(DeptEditRequest request) {
        ValidationUtil.redoCheck(sysDeptMapper, SysDept::getTitle, request.getTitle(), wrapper -> wrapper.eq(SysDept::getParentCode, request.getParentCode()), SysDept::getId, request.getId(), ErrorCode.DEPARTMENT_TITLE_REPEAT, "部门名称重复 [{}] [{}]");
        DataUtil.copy(request, SysDept.class, sysDeptMapper::updateById);
    }

    @Override
    public void deleteById(Long id) {
        sysDeptMapper.deleteById(id);
    }

    /**
     * 根据列表计算出子级部门下一个编码的值
     * 初始编号默认101,后面依次累计+1
     *
     * @param code 部门编号
     * @return 下一个编号
     */
    private String getNextCode(String code) {
        String maxCode = sysDeptMapper.getMaxCodeChild(code);
        return commonService.generateNextId(maxCode, code, STEP_100, ErrorCode.DEPARTMENT_DEPTH_ERROR);
    }

}
