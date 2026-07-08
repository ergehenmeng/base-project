package com.eghm.service.sys;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.SmsLogQueryRequest;
import com.eghm.sys.model.SmsLog;
import com.eghm.vo.operate.log.SmsLogResponse;

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
