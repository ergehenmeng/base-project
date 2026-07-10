package com.eghm.application.system.service;

import com.eghm.domain.system.model.SmsLog;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
public interface SmsLogApplicationService {

    /**
     * 添加短信记录
     *
     * @param smsLog smsLog
     */
    void addSmsLog(SmsLog smsLog);

}
