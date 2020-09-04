package com.eghm.dao.mapper;

import com.eghm.dao.model.LoginDevice;

/**
 * @author 二哥很猛
 */
public interface LoginDeviceMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(LoginDevice record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    LoginDevice selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(LoginDevice record);

}