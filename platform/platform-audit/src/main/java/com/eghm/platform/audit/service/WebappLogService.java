package com.eghm.platform.audit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.audit.dto.WebappQueryRequest;
import com.eghm.platform.audit.entity.WebappLog;
import com.eghm.platform.audit.vo.WebappLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogService {

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
