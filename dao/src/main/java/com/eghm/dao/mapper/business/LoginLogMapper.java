package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.LoginLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface LoginLogMapper {

    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(LoginLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    LoginLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(LoginLog record);

    /**
     * 获取最近一次的登陆信息
     * @param userId userId
     * @return 登陆信息
     */
    LoginLog getLastLogin(@Param("userId")Integer userId);

}