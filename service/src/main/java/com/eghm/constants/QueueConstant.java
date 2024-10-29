package com.eghm.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * mq队列定义常亮
 *
 * @author 二哥很猛
 * @since 2022/7/28
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QueueConstant {

    /**
     * 管理后台操作日志队列
     */
    public static final String MANAGE_LOG_QUEUE = "manage_log_queue";

    /**
     * 移动端登陆日志队列, 主要记录用户登陆设备信息
     */
    public static final String LOGIN_LOG_QUEUE = "login_log_queue";

    /**
     * 移动端操作日志队列
     */
    public static final String WEBAPP_LOG_QUEUE = "webapp_log_queue";

}
