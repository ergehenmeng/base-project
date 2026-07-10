package com.eghm.application.system.service;

import com.eghm.domain.system.model.WebappLog;
import com.eghm.domain.system.repository.WebappLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
@Service
@AllArgsConstructor
public class WebappLogApplicationService {

    private final WebappLogRepository webappLogRepository;

    /**
     * 添加系统异常日志
     *
     * @param log 日志信息
     */
    public void insertWebappLog(WebappLog log) {
        webappLogRepository.save(log);
    }
}
