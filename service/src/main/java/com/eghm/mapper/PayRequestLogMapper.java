package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.model.PayRequestLog;
import com.eghm.vo.operate.log.PayRequestLogResponse;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 支付或退款请求记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogMapper extends BaseMapper<PayRequestLog> {

    /**
     * 分页查询
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<PayRequestLogResponse> getByPage(Page<PayRequestLogResponse> page, @Param("param") PayLogQueryRequest request);
}
