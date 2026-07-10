package com.eghm.application.payment.service;

import com.eghm.application.payment.dto.PrepayDTO;
import com.eghm.application.payment.dto.RefundDTO;
import com.eghm.application.payment.vo.PrepayVO;
import com.eghm.application.payment.vo.RefundVO;

/**
 * <p>
 * 支付或退款请求记录表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogApplicationService {

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
