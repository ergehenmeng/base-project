package com.eghm.dao.mapper;

import com.eghm.dao.model.UserInMail;

/**
 * @author 二哥很猛
 */
public interface UserInMailMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserInMail record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserInMail selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(UserInMail record);

}