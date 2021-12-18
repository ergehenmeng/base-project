package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.LoginLog;
import org.apache.ibatis.annotations.Param;

/**
 * @author 二哥很猛
 */
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 获取最近一次的登陆信息
     * @param userId userId
     * @return 登陆信息
     */
    LoginLog getLastLogin(@Param("userId")Long userId);

    /**
     * 逻辑删除用户登陆日志信息
     * @param userId 用户id
     * @param serialNumber 设备号
     * @return 批量删除
     */
    int deleteLoginLog(@Param("userId") Long userId, @Param("serialNumber") String serialNumber);
}