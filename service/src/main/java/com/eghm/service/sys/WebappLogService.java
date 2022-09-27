package com.eghm.service.sys;

import com.eghm.model.WebappLog;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
public interface WebappLogService {

    /**
     * 添加系统异常日志
     * @param log 日志信息
     */
    void insertWebappLog(WebappLog log);
}
