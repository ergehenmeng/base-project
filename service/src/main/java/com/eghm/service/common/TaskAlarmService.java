package com.eghm.service.common;

import com.eghm.configuration.task.config.TaskDetail;

/**
 * @author 殿小二
 * @date 2020/9/18
 */
public interface TaskAlarmService {

    /**
     * 报警通知
     * @param detail 任务配置信息
     * @param errorMsg 错误信息
     */
    void noticeAlarm(TaskDetail detail, String errorMsg);
}
