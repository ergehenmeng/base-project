package com.fanyin.service.user;

import com.fanyin.model.dto.login.LoginRecord;

/**
 * @author 二哥很猛
 * @date 2019/8/28 15:24
 */
public interface LoginLogService {

    /**
     * 添加操作日志
     * @param record 操作日志
     */
    void addLoginLog(LoginRecord record);

}
