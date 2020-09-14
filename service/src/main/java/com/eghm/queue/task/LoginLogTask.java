package com.eghm.queue.task;

import com.eghm.model.dto.ext.LoginRecord;
import com.eghm.queue.AbstractTask;
import com.eghm.service.user.LoginLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @date 2019/9/12 11:40
 */
@Slf4j
public class LoginLogTask extends AbstractTask<LoginRecord, LoginLogService> {


    public LoginLogTask(LoginRecord data, LoginLogService loginLogService) {
        super(data, loginLogService);
    }

    @Override
    protected void execute(LoginRecord data) {
        getBean().addLoginLog(data);
    }

    @Override
    protected void doException(Exception e) {
        log.error("登陆日志保存异常", e);
    }
}
