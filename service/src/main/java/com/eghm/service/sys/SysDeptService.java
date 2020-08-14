package com.eghm.service.sys;

import com.eghm.model.dto.sys.department.DepartmentAddRequest;
import com.eghm.model.dto.sys.department.DepartmentEditRequest;
import com.eghm.dao.model.sys.SysDept;

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
    SysDept getById(Integer id);

    /**
     * 获取所有的部门信息
     * @return 列表
     */
    List<SysDept> getDepartment();

    /**
     * 添加部门
     * @param request 前台参数
     */
    void addDepartment(DepartmentAddRequest request);

    /**
     * 编辑部门节点信息
     * @param request 前天参数
     */
    void editDepartment(DepartmentEditRequest request);

    /**
     * 根据列表计算出子级部门下一个编码的值
     * 初始编号默认101,后面依次累计+1
     * @param code 部门编号
     * @return 下一个编号
     */
    String getNextCode(String code);
}

