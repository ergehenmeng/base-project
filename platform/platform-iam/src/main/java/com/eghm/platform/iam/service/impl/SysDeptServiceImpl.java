package com.eghm.platform.iam.service.impl;

import com.eghm.platform.config.service.CommonService;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.security.UserToken;
import com.eghm.platform.iam.dto.DeptAddRequest;
import com.eghm.platform.iam.dto.DeptEditRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.platform.iam.mapper.SysDeptMapper;
import com.eghm.platform.iam.entity.SysDept;
import com.eghm.platform.iam.service.SysDeptService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.foundation.web.utility.TreeUtil;
import com.eghm.foundation.web.utility.ValidationUtil;
import com.eghm.platform.iam.vo.SysDeptResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.foundation.core.constants.CommonConstant.ROOT_NODE;
import static com.eghm.foundation.core.constants.CommonConstant.STEP_100;

/**
 * 部门 service
 *
 * @author 二哥很猛
 * @since 2018/12/13 16:49
 */
@Slf4j
@Service
@AllArgsConstructor
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
