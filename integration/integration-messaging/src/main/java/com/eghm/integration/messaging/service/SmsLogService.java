package com.eghm.integration.messaging.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.integration.messaging.dto.SmsLogQueryRequest;
import com.eghm.integration.messaging.entity.SmsLog;
import com.eghm.integration.messaging.vo.SmsLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
public interface SmsLogService {

    /**
     * 根据条件查询短信记录列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<SmsLogResponse> getByPage(SmsLogQueryRequest request);

    /**
     * 添加短信记录
     *
     * @param smsLog smsLog
     */
    void addSmsLog(SmsLog smsLog);

}
