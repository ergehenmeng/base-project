package com.eghm.dao.mapper.user;

import com.eghm.dao.model.business.UserExt;

/**
 * @author 二哥很猛
 */
public interface UserExtMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(UserExt record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    UserExt selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(UserExt record);

}