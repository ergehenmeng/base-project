package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.log.ManageQueryRequest;
import com.eghm.model.ManageLog;
import com.eghm.vo.log.ManageLogResponse;

/**
 * @author 二哥很猛
 * @since 2019/1/15 17:54
 */
public interface ManageLogService {

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

