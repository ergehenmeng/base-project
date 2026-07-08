package com.eghm.service.sys.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.service.sys.WebappLogQueryGateway;
import com.eghm.service.sys.WebappLogService;
import com.eghm.sys.model.WebappLog;
import com.eghm.sys.repository.WebappLogRepository;
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
