package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.SmsLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface SmsLogMapper extends BaseMapper<SmsLog> {

    /**
     * 统计一段时间内发送短信的次数 成功或者发送中的短信
     *
     * @param smsType   短信类型
     * @param mobile    手机号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 条数
     */
    int countSms(@Param("smsType") String smsType, @Param("mobile") String mobile, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

}