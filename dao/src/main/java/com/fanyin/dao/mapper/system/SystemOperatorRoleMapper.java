package com.fanyin.dao.mapper.system;

import com.fanyin.dao.model.system.SystemOperatorRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SystemOperatorRoleMapper {

    /**
     * 插入不为空的记录
     *
     * @param record
     */
    int insertSelective(SystemOperatorRole record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SystemOperatorRole selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SystemOperatorRole record);


    /**
     * 根据用户id查询角色id列表
     * @param operatorId 角色id
     * @return 角色id列表
     */
    List<Integer> getByOperatorId(@Param("operatorId") Integer operatorId);

    /**
     * 删除用户所有的角色
     * @param operatorId 管理人员id
     * @return 影响条数
     */
    int deleteByOperatorId(@Param("operatorId") Integer operatorId);

    /**
     * 批量添加用户角色关系信息
     * @param operatorId 用户id
     * @param roleList 角色列表
     * @return 条数
     */
    int batchInsertOperatorRole(@Param("operatorId") Integer operatorId, @Param("roleList") List<Integer> roleList);
}