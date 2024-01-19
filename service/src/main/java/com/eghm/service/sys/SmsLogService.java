package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sms.SmsLogQueryRequest;
import com.eghm.model.SmsLog;

import java.util.Date;

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
    Page<SmsLog> getByPage(SmsLogQueryRequest request);

    /**
     * 添加短信记录
     *
     * @param smsLog smsLog
     */
    void addSmsLog(SmsLog smsLog);

    /**
     * 统计一段时间内发送短信的次数 成功或者发送中的短信
     *
     * @param smsType   短信类型
     * @param mobile    手机号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 条数
     */
    int countSms(String smsType, String mobile, Date startTime, Date endTime);

}
