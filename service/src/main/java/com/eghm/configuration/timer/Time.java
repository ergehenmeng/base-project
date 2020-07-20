package com.eghm.configuration.timer;

import java.util.concurrent.TimeUnit;

/**
 * 工具类
 * @author 二哥很猛
 * @date 2018/9/11 9:52
 */
public class Time {

    private Time() {
    }
    /**
     * 获取系统nanoTime,并纳秒值转换为毫秒值
     * @return 毫秒值
     */
    public static long getHiresClockMs(){
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
    }
}
