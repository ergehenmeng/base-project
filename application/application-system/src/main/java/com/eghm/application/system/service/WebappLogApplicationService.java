package com.eghm.application.system.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.WebappQueryRequest;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.application.shared.vo.operate.log.WebappLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogApplicationService {

    /**
     * 分页查询列表
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<WebappLogResponse> getByPage(WebappQueryRequest request);

    /**
     * 添加系统异常日志
     *
     * @param log 日志信息
     */
    void insertWebappLog(WebappLog log);

}
