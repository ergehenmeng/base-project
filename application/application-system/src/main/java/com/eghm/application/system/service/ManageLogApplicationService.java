package com.eghm.application.system.service;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.ManageQueryRequest;
import com.eghm.domain.system.model.ManageLog;
import com.eghm.application.shared.vo.operate.log.ManageLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/1/15 17:54
 */
public interface ManageLogApplicationService {

    /**
     * 根据条件分页查询操作日期信息
     *
     * @param request 查询条件
     * @return 分页列表
     */
    Page<ManageLogResponse> getByPage(ManageQueryRequest request);

    /**
     * 添加操作日志
     *
     * @param log 日志
     */
    void insertManageLog(ManageLog log);

}

