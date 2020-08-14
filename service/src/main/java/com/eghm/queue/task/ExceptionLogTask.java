package com.eghm.queue.task;

import com.eghm.dao.model.sys.ExceptionLog;
import com.eghm.queue.AbstractTask;
import com.eghm.service.sys.ExceptionLogService;

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
