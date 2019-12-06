package com.fanyin.queue.task;

import com.fanyin.dao.model.system.ExceptionLog;
import com.fanyin.queue.AbstractTask;
import com.fanyin.service.system.ExceptionLogService;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:31
 */
public class ExceptionLogTask extends AbstractTask<ExceptionLog, ExceptionLogService> {

    public ExceptionLogTask(ExceptionLog data, ExceptionLogService bean) {
        super(data, bean);
    }

    @Override
    protected void execute(ExceptionLog data) {
        getBean().insertExceptionLog(data);
    }

}
