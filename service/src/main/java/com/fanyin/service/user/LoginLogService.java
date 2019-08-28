package com.fanyin.service.user;

import com.fanyin.dao.model.business.LoginLog;
import com.fanyin.model.dto.login.LoginRecord;

/**
 * @author 二哥很猛
 * @date 2019/8/28 15:24
 */
public interface LoginLogService {

    /**
     * 添加登陆日志
     * @param record 登陆日志
     */
    void addLoginLog(LoginRecord record);

    /**
     * 获取用户最近一次登陆的信息, 首次登陆系统时结果为空
     * @param userId 用户id
     * @return 登陆信息
     */
    LoginLog getLastLogin(Integer userId);
}
