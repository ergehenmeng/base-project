package com.eghm.application.system.service;

import com.eghm.domain.system.model.WebappLog;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogApplicationService {

    /**
     * 添加系统异常日志
     *
     * @param log 日志信息
     */
    void insertWebappLog(WebappLog log);

}
