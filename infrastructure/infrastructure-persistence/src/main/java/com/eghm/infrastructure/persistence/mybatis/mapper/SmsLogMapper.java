package com.eghm.infrastructure.persistence.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.po.SmsLogPO;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @author eghm
 */
public interface SmsLogMapper extends BaseMapper<SmsLogPO> {

    /**
     * 分页查询
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<SmsLogResponse> getByPage(Page<SmsLogResponse> page, @Param("param") SmsLogQueryRequest request);

}
