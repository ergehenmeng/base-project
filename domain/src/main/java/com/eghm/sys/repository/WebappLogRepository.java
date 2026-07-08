package com.eghm.sys.repository;

import com.eghm.sys.model.WebappLog;

/**
 * 移动端日志仓储接口
 *
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
public interface WebappLogRepository {

    /**
     * 添加系统异常日志
     *
     * @param log 日志信息
     */
    void save(WebappLog log);
}
