package com.eghm.application.system.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.domain.system.model.SmsLog;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
public interface SmsLogApplicationService {

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
