package com.eghm.application.payment.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.pay.PayLogQueryRequest;
import com.eghm.application.shared.vo.operate.log.PayRequestLogResponse;

/**
 * 支付或退款请求记录查询端口
 *
 * @author 二哥很猛
 * @since 2023-06-13
 */
public interface PayRequestLogQueryService {

    Page<PayRequestLogResponse> getByPage(Page<PayRequestLogResponse> page, PayLogQueryRequest request);
}
