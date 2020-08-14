package com.eghm.dao.mapper.sys;

import com.eghm.dao.model.business.SmsLog;
import com.eghm.model.dto.business.sms.SmsLogQueryRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SmsLogMapper {


    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(SmsLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    SmsLog selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(SmsLog record);


    /**
     * 统计一段时间内发送短信的次数 成功或者发送中的短信
     * @param smsType 短信类型
     * @param mobile 手机号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 条数
     */
    int countSms(@Param("smsType") String smsType, @Param("mobile") String mobile, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 根据条件查询
     * @param request 查询条件
     * @return 短信记录列表
     */
    List<SmsLog> getList(SmsLogQueryRequest request);
}