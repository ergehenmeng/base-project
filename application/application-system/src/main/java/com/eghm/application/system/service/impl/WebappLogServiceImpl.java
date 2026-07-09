package com.eghm.application.system.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.application.system.service.WebappLogQueryGateway;
import com.eghm.application.system.service.WebappLogService;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.domain.system.repository.WebappLogRepository;
import com.eghm.vo.operate.log.WebappLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
@AllArgsConstructor
@Service("webappLogService")
public class WebappLogServiceImpl implements WebappLogService {

    private final WebappLogRepository webappLogRepository;

    private final WebappLogQueryGateway webappLogQueryGateway;

    @Override
    public Page<WebappLogResponse> getByPage(WebappQueryRequest request) {
        return webappLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Override
    public void insertWebappLog(WebappLog log) {
        webappLogRepository.save(log);
    }
}
