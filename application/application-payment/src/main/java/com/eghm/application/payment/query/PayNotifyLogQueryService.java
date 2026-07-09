package com.eghm.application.payment.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.pay.PayLogQueryRequest;
import com.eghm.application.shared.vo.operate.log.PayNotifyLogResponse;

/**
 * 支付异步通知日志查询端口
 *
 * @author 二哥很猛
 * @since 2022/7/26
 */
public interface PayNotifyLogQueryService {

    Page<PayNotifyLogResponse> getByPage(Page<PayNotifyLogResponse> page, PayLogQueryRequest request);
}
