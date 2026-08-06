package com.eghm.platform.audit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.audit.dto.WebappQueryRequest;
import com.eghm.platform.audit.mapper.WebappLogMapper;
import com.eghm.platform.audit.entity.WebappLog;
import com.eghm.platform.audit.service.WebappLogService;
import com.eghm.platform.audit.vo.WebappLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/12/6 16:38
 */
@Service
@AllArgsConstructor
public class WebappLogServiceImpl implements WebappLogService {

    private final WebappLogMapper webappLogMapper;

    @Override
    public Page<WebappLogResponse> getByPage(WebappQueryRequest request) {
        return webappLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void insertWebappLog(WebappLog log) {
        webappLogMapper.insert(log);
    }

}
