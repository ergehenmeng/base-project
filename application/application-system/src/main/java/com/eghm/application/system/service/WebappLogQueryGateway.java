package com.eghm.application.system.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.vo.operate.log.WebappLogResponse;

/**
 * 移动端日志查询端口
 *
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogQueryGateway {

    Page<WebappLogResponse> getByPage(Page<WebappLogResponse> page, WebappQueryRequest request);
}
