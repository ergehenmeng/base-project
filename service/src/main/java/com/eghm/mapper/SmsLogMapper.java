package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.operate.sms.SmsLogQueryRequest;
import com.eghm.model.SmsLog;
import com.eghm.vo.log.SmsLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author eghm
 */
public interface SmsLogMapper extends BaseMapper<SmsLog> {

    /**
     * 分页查询
     *
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SmsLogResponse> getByPage(Page<SmsLogResponse> page, @Param("param") SmsLogQueryRequest request);

}