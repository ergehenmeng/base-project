package com.eghm.service.sys;

import com.eghm.dto.dept.DeptAddRequest;
import com.eghm.dto.dept.DeptEditRequest;
import com.eghm.model.SysDept;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/12/13 16:49
 */
public interface SysDeptService {

    /**
     * 主键查询部门节点信息
     * @param id 主键
     * @return 部门节点
     */
    SysDept getById(Long id);

    /**
     * 获取所有的部门信息
     * @return 列表
     */
    List<SysDept> getList();

    /**
     * 添加部门
     * @param request 前台参数
     */
    void create(DeptAddRequest request);

    /**
     * 编辑部门节点信息
     * @param request 前天参数
     */
    void update(DeptEditRequest request);
}

