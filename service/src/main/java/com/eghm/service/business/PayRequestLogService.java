package com.eghm.service.business;

import com.eghm.service.pay.dto.PrepayDTO;
import com.eghm.service.pay.dto.RefundDTO;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.service.pay.vo.RefundVO;

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
     * 添加请求支付的日志
     * @param request 请求参数
     * @param response 响应参数
     */
    void insertPayLog(PrepayDTO request, PrepayVO response);

    /**
     * 添加请求退款的日志
     * @param request 请求参数
     * @param response 响应参数
     */
    void insertRefundLog(RefundDTO request, RefundVO response);
}
