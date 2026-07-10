package com.eghm.application.system.service;

import com.eghm.application.shared.common.CommonService;
import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.UserToken;
import com.eghm.application.shared.dto.sys.dept.DeptAddRequest;
import com.eghm.application.shared.dto.sys.dept.DeptEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.query.SysDeptQueryService;
import com.eghm.domain.system.model.SysDept;
import com.eghm.domain.system.repository.SysDeptRepository;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.utils.TreeUtil;
import com.eghm.application.shared.vo.sys.ext.SysDeptResponse;
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
@Service
@AllArgsConstructor
public class SysDeptApplicationService {

    private final SysDeptRepository sysDeptRepository;

    private final SysDeptQueryService sysDeptQueryService;

    private final CommonService commonService;

    /**
     * 获取所有的部门信息
     *
     * @param query 查询条件
     * @return 列表
     */
    public List<SysDeptResponse> getList(PagingQuery query) {
        List<SysDeptResponse> responseList = sysDeptQueryService.getList(query);
        return TreeUtil.tree(responseList, ROOT_NODE, SysDeptResponse::getCode, SysDeptResponse::getParentCode, SysDeptResponse::setChildren);
    }

    /**
     * 添加部门
     *
     * @param request 前台参数
     */
    public void create(DeptAddRequest request) {
        if (sysDeptRepository.existsByParentCodeAndTitle(request.getParentCode(), request.getTitle(), null)) {
            log.warn("部门名称重复 [{}] [{}]", request.getParentCode(), request.getTitle());
            throw new BusinessException(ErrorCode.DEPARTMENT_TITLE_REPEAT);
        }
        SysDept department = DataUtil.copy(request, SysDept.class);
        String code = this.getNextCode(request.getParentCode());
        department.assignCode(code);
        UserToken user = SecurityHolder.getUserRequired();
        department.recordOperator(user.getId(), user.getNickName());
        sysDeptRepository.save(department);
    }

    /**
     * 编辑部门节点信息
     *
     * @param request 前天参数
     */
    public void update(DeptEditRequest request) {
        if (sysDeptRepository.existsByParentCodeAndTitle(request.getParentCode(), request.getTitle(), request.getId())) {
            log.warn("部门名称重复 [{}] [{}]", request.getParentCode(), request.getTitle());
            throw new BusinessException(ErrorCode.DEPARTMENT_TITLE_REPEAT);
        }
        sysDeptRepository.update(DataUtil.copy(request, SysDept.class));
    }

    /**
     * 逻辑删除部门信息
     *
     * @param id id
     */
    public void deleteById(Long id) {
        sysDeptRepository.deleteById(id);
    }

    /**
     * 根据列表计算出子级部门下一个编码的值
     * 初始编号默认101,后面依次累计+1
     *
     * @param code 部门编号
     * @return 下一个编号
     */
    private String getNextCode(String code) {
        String maxCode = sysDeptRepository.findMaxChildCode(code);
        return commonService.generateNextId(maxCode, code, STEP_100, ErrorCode.DEPARTMENT_DEPTH_ERROR);
    }
}
