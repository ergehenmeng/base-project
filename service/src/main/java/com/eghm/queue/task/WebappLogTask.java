package com.eghm.queue.task;

import com.eghm.dao.model.WebappLog;
import com.eghm.queue.AbstractTask;
import com.eghm.service.sys.WebappLogService;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:31
 */
public class WebappLogTask extends AbstractTask<WebappLog, WebappLogService> {

    public WebappLogTask(WebappLog data, WebappLogService bean) {
        super(data, bean);
    }

    @Override
    protected void execute(WebappLog data) {
        getBean().insertWebappLog(data);
    }

}
