package com.eghm.sys.repository;

import com.eghm.sys.model.SmsLog;

/**
 * 短信日志仓储接口
 *
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
public interface SmsLogRepository {

    /**
     * 添加短信记录
     *
     * @param smsLog smsLog
     */
    void save(SmsLog smsLog);
}
