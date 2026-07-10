package com.eghm.application.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.WebappQueryRequest;
import com.eghm.application.shared.vo.operate.log.WebappLogResponse;

/**
 * 移动端日志查询服务
 *
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogQueryService {

    Page<WebappLogResponse> getByPage(Page<WebappLogResponse> page, WebappQueryRequest request);
}
