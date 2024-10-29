package com.eghm.pay;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.dto.RefundDTO;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.pay.vo.RefundVO;
import com.eghm.vo.business.log.PayRequestLogResponse;

/**
 * <p>
 * 支付或退款请求记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogService {

    /**
     * 分页查询支付退款同步请求的日志
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<PayRequestLogResponse> getByPage(PayLogQueryRequest request);

    /**
     * 添加请求支付的日志
     *
     * @param request  请求参数
     * @param response 响应参数
     */
    void insertPayLog(PrepayDTO request, PrepayVO response);

    /**
     * 添加请求退款的日志
     *
     * @param request  请求参数
     * @param response 响应参数
     */
    void insertRefundLog(RefundDTO request, RefundVO response);
}
