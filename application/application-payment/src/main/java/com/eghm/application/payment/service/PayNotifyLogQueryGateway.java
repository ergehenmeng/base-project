package com.eghm.application.payment.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.vo.operate.log.PayNotifyLogResponse;

/**
 * 支付异步通知日志查询端口
 *
 * @author 二哥很猛
 * @since 2022/7/26
 */
public interface PayNotifyLogQueryGateway {

    Page<PayNotifyLogResponse> getByPage(Page<PayNotifyLogResponse> page, PayLogQueryRequest request);
}
