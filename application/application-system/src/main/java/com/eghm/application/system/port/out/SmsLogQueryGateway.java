package com.eghm.application.system.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;

/**
 * 短信日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/8/16 18:47
 */
public interface SmsLogQueryGateway {

    Page<SmsLogResponse> getByPage(Page<SmsLogResponse> page, SmsLogQueryRequest request);
}
