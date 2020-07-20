package com.eghm.queue.task;

import com.eghm.dao.model.system.SystemOperationLog;
import com.eghm.queue.AbstractTask;
import com.eghm.service.system.OperationLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志任务
 *
 * @author 二哥很猛
 * @date 2019/1/15 17:58
 */
@Slf4j
public class OperationLogTask extends AbstractTask<SystemOperationLog, OperationLogService> {

    public OperationLogTask(SystemOperationLog data, OperationLogService logService) {
        super(data, logService);
    }

    @Override
    protected void execute(SystemOperationLog data) {
        getBean().insertOperationLog(data);
    }

    @Override
    protected void doException(Exception e) {
        log.error("操作日志写入异常 data:[{}]", getData(), e);
    }

    @Override
    protected void doFinally() {
    }
}
