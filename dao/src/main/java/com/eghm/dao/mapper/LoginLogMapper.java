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

}