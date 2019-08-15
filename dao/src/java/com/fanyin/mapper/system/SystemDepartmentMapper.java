package com.fanyin.mapper.system;

import com.fanyin.model.system.SystemDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemDepartmentMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SystemDepartment record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SystemDepartment selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SystemDepartment record);

    /**
     * 获取部门下的子级部门最大编号的部门
     * @param code 部门编号
     * @return 子部门列表
     */
    SystemDepartment getMaxCodeChild(@Param("code") String code);

    /**
     * 获取所有的部门
     * @return 部门列表
     */
    List<SystemDepartment> getList();
}