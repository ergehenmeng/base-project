package com.eghm.service.common;

import com.eghm.configuration.task.config.Task;

/**
 * @author 殿小二
 * @since 2020/9/18
 */
public interface TaskAlarmService {

    /**
     * 报警通知
     *
     * @param task     任务配置信息
     * @param errorMsg 错误信息
     */
    void noticeAlarm(Task task, String errorMsg);
}
