package com.eghm.common.impl;

import com.eghm.common.AlarmService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Slf4j
public class DefaultAlarmServiceImpl implements AlarmService {

    @Override
    public void sendMsg(String content) {
        log.info("报警渠道未配置,默认采用日志打印: [{}]", content);
    }
}
