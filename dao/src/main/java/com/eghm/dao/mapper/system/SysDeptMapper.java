package com.eghm.dao.mapper.system;

import com.eghm.dao.model.sys.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysDeptMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SysDept record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SysDept selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SysDept record);

    /**
     * 获取部门下的子级部门最大编号的部门
     * @param code 部门编号
     * @return 子部门列表
     */
    SysDept getMaxCodeChild(@Param("code") String code);

    /**
     * 获取所有的部门
     * @return 部门列表
     */
    List<SysDept> getList();
}