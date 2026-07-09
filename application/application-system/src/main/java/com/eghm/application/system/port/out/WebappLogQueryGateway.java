package com.eghm.application.system.port.out;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.WebappQueryRequest;
import com.eghm.application.shared.vo.operate.log.WebappLogResponse;

/**
 * 移动端日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogQueryGateway {

    Page<WebappLogResponse> getByPage(Page<WebappLogResponse> page, WebappQueryRequest request);
}
