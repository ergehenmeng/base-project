package com.eghm.application.system.service;

import com.eghm.domain.system.model.ManageLog;

/**
 * @author 二哥很猛
 * @since 2019/1/15 17:54
 */
public interface ManageLogApplicationService {

    /**
     * 添加操作日志
     *
     * @param log 日志
     */
    void insertManageLog(ManageLog log);

}

