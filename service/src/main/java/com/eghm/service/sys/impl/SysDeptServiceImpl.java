package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.dto.ext.SecurityHolder;
import com.eghm.dto.sys.dept.DeptAddRequest;
import com.eghm.dto.sys.dept.DeptEditRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.UserToken;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysDeptMapper;
import com.eghm.model.SysDept;
import com.eghm.service.sys.SysDeptService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.sys.ext.SysDeptResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.eghm.constants.CommonConstant.ROOT_NODE;

/**
 * 部门 service
 *
 * @author 二哥很猛
 * @since 2018/12/13 16:49
 */
@Service("sysDeptService")
@Slf4j
@AllArgsConstructor
public class SysDeptServiceImpl implements SysDeptService {

    /**
     * 部门步长 即:一个部门对多有900个直属部门 100~999
     */
    private static final String STEP = "100";

    private final SysDeptMapper sysDeptMapper;

    @Override
    public List<SysDeptResponse> getList(PagingQuery query) {
        List<SysDeptResponse> responseList = sysDeptMapper.getList(query.getQueryName());
        return this.treeBin(ROOT_NODE, responseList);
    }

    @Override
    public void create(DeptAddRequest request) {
        this.redoTitle(request.getTitle(), request.getParentCode(), null);
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
        this.redoTitle(request.getTitle(), request.getParentCode(), request.getId());
        SysDept department = DataUtil.copy(request, SysDept.class);
        sysDeptMapper.updateById(department);
    }

    @Override
    public void deleteById(Long id) {
        sysDeptMapper.deleteById(id);
    }

    /**
     * 判断部门是否重复
     *
     * @param title      部门名称
     * @param parentCode 父节点
     * @param id         id
     */
    private void redoTitle(String title, String parentCode, Long id) {
        LambdaQueryWrapper<SysDept> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SysDept::getTitle, title);
        wrapper.eq(SysDept::getParentCode, parentCode);
        wrapper.ne(SysDept::getId, id);
        if (sysDeptMapper.selectCount(wrapper) > 0) {
            throw new BusinessException(ErrorCode.DEPARTMENT_TITLE_REPEAT);
        }
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
        if (maxCode == null) {
            return ROOT_NODE.equals(code) ? STEP : code + STEP;
        }
        // 不校验子部门上限,傻子才会有900个部门
        try {
            return String.valueOf(Long.parseLong(maxCode) + 1);
        } catch (NumberFormatException e) {
            log.warn("部门编号生成失败 code:[{}]", code);
            throw new BusinessException(ErrorCode.DEPARTMENT_DEPTH_ERROR);
        }
    }

    /**
     * 将菜单列表树化
     *
     * @param menuList 菜单列表
     * @return 菜单列表 树状结构
     */
    private List<SysDeptResponse> treeBin(String code, List<SysDeptResponse> menuList) {
        List<SysDeptResponse> responseList = menuList.stream().filter(parent -> Objects.equals(code, parent.getParentCode())).collect(Collectors.toList());
        responseList.forEach(parent -> parent.setChildren(this.treeBin(parent.getCode(), menuList)));
        return responseList;
    }

}
