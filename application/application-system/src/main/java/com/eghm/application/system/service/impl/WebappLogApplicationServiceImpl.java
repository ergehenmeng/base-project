package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.WebappQueryRequest;
import com.eghm.application.system.query.WebappLogQueryService;
import com.eghm.application.system.service.WebappLogApplicationService;
import com.eghm.domain.system.model.WebappLog;
import com.eghm.domain.system.repository.WebappLogRepository;
import com.eghm.application.shared.vo.operate.log.WebappLogResponse;
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

    private final WebappLogQueryService webappLogQueryGateway;

    @Override
    public Page<WebappLogResponse> getByPage(WebappQueryRequest request) {
        return webappLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Override
    public void insertWebappLog(WebappLog log) {
        webappLogRepository.save(log);
    }
}
