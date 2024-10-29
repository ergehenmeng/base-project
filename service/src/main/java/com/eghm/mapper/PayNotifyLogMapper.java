package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.model.PayNotifyLog;
import com.eghm.vo.business.log.PayNotifyLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 支付异步通知记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-26
 */
public interface PayNotifyLogMapper extends BaseMapper<PayNotifyLog> {

    /**
     * 分页查询
     *
     * @param page    分页
     * @param request 查询条件
     * @return 列表
     */
    Page<PayNotifyLogResponse> getByPage(Page<PayNotifyLogResponse> page, @Param("param") PayLogQueryRequest request);
}
