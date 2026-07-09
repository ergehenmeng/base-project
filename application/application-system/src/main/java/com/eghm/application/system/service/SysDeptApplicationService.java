package com.eghm.application.system.service;

import com.eghm.application.shared.dto.sys.dept.DeptAddRequest;
import com.eghm.application.shared.dto.sys.dept.DeptEditRequest;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.vo.sys.ext.SysDeptResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2018/12/13 16:49
 */
public interface SysDeptApplicationService {

    /**
     * 获取所有的部门信息
     *
     * @param query 查询条件
     * @return 列表
     */
    List<SysDeptResponse> getList(PagingQuery query);

    /**
     * 添加部门
     *
     * @param request 前台参数
     */
    void create(DeptAddRequest request);

    /**
     * 编辑部门节点信息
     *
     * @param request 前天参数
     */
    void update(DeptEditRequest request);

    /**
     * 逻辑删除部门信息
     *
     * @param id id
     */
    void deleteById(Long id);
}

