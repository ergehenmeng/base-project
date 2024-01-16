package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SysUserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 二哥很猛
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id查询角色id列表
     *
     * @param userId 角色id
     * @return 角色id列表
     */
    List<Long> getByUserId(@Param("userId") Long userId);

    /**
     * 删除用户所有的角色
     *
     * @param userId 管理人员id
     */
    @Delete("delete from sys_user_role where user_id = #{userId} ")
    void deleteByUserId(@Param("userId") Long userId);

}