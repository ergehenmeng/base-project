package com.eghm.dao.mapper;

import com.eghm.dao.model.UserInviteLog;

/**
 * @author 二哥很猛
 */
public interface UserInviteLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserInviteLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserInviteLog selectByPrimaryKey(Long id);

}