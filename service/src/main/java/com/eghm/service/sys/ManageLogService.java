package com.eghm.service.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.ManageLog;
import com.eghm.dto.log.ManageQueryRequest;

/**
 * @author 二哥很猛
 * @date 2019/1/15 17:54
 */
public interface ManageLogService {

    /**
     * 根据条件分页查询操作日期信息
     * @param request 查询条件
     * @return 分页列表
     */
    Page<ManageLog> getByPage(ManageQueryRequest request);

    /**
     * 添加操作日志
     * @param log 日志
     */
    void insertManageLog(ManageLog log);

    /**
     * 根据主键查询响应信息
     * @param id id
     * @return 响应结果 可能为空
     */
    String getResponseById(Long id);
}

