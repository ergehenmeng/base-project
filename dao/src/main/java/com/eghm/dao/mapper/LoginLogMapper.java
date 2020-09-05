package com.eghm.dao.mapper;

import com.eghm.dao.model.LoginLog;
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

    /**
     * 逻辑删除用户登陆日志信息
     * @param userId 用户id
     * @param serialNumber 设备号
     * @return 批量删除
     */
    int deleteLoginLog(@Param("userId") Integer userId, @Param("serialNumber") String serialNumber);
}