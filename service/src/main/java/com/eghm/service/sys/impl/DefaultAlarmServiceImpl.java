package com.eghm.service.sys.impl;

import com.eghm.service.sys.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2024/3/25
 */

@Slf4j
@Service("defaultAlarmService")
public class DefaultAlarmServiceImpl implements AlarmService {

    @Override
    public void sendMsg(String content) {
        log.info("未设置报警: [{}]", content);
    }
}
