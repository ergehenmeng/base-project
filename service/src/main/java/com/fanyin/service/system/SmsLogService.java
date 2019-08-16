package com.fanyin.service.system;

import com.fanyin.dao.model.system.SmsLog;

/**
 * @author 二哥很猛
 * @date 2019/8/16 18:47
 */
public interface SmsLogService {

    /**
     * 添加短信记录
     * @param smsLog smsLog
     */
    void addSmsLog(SmsLog smsLog);
}
