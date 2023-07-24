package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.log.WebappQueryRequest;
import com.eghm.mapper.WebappLogMapper;
import com.eghm.model.WebappLog;
import com.eghm.service.sys.WebappLogService;
import com.eghm.vo.log.WebappLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/12/6 16:38
 */
@Service("webappLogService")
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
