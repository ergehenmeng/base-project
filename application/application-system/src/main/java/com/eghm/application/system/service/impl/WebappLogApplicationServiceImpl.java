package com.eghm.application.system.service.impl;

import com.eghm.application.system.service.WebappLogApplicationService;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.domain.system.repository.WebappLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
@AllArgsConstructor
@Service("webappLogService")
public class WebappLogApplicationServiceImpl implements WebappLogApplicationService {

    private final WebappLogRepository webappLogRepository;

    @Override
    public void insertWebappLog(WebappLog log) {
        webappLogRepository.save(log);
    }
}
