package com.fanyin.service.system;

import com.fanyin.dto.system.log.OperationQueryRequest;
import com.fanyin.model.system.SystemOperationLog;
import com.github.pagehelper.PageInfo;

/**
 * @author 二哥很猛
 * @date 2019/1/15 17:54
 */
public interface OperationLogService {

    /**
     * 添加操作日志
     * @param log 日志
     */
    void insertOperationLog(SystemOperationLog log);

    /**
     * 根据条件分页查询操作日期信息
     * @param request 查询条件
     * @return 分页列表
     */
    PageInfo<SystemOperationLog> getByPage(OperationQueryRequest request);

    /**
     * 根据主键查询响应信息
     * @param id id
     * @return 响应结果 可能为空
     */
    String getResponseById(Integer id);
}

