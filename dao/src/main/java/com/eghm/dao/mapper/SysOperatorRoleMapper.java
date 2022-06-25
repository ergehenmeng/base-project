package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.SysOperatorRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysOperatorRoleMapper extends BaseMapper<SysOperatorRole> {

    /**
     * 根据用户id查询角色id列表
     * @param operatorId 角色id
     * @return 角色id列表
     */
    List<Long> getByOperatorId(@Param("operatorId") Long operatorId);

    /**
     * 删除用户所有的角色
     * @param operatorId 管理人员id
     */
    @Delete("delete from sys_operator_role where operator_id = #{operatorId} ")
    void deleteByOperatorId(@Param("operatorId") Long operatorId);

}