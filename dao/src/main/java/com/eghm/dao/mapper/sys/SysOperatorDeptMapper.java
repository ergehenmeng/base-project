package com.eghm.dao.mapper.sys;

import com.eghm.dao.model.sys.SysOperatorDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysOperatorDeptMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(SysOperatorDept record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SysOperatorDept selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(SysOperatorDept record);

    /**
     * 获取用户所拥有的所有部门(数据权限)
     * @param operatorId 用户id
     * @return 部门id
     */
    List<Integer> getDeptList(@Param("operatorId") Integer operatorId);

    /**
     * 删除用户对应的部门的数据权限
     * @param operatorId 用户id
     */
    void deleteByOperatorId(@Param("operatorId") Integer operatorId);
}