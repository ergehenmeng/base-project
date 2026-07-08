package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.mapper.WebappLogMapper;
import com.eghm.service.sys.WebappLogQueryGateway;
import com.eghm.vo.operate.log.WebappLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisWebappLogQueryGateway implements WebappLogQueryGateway {

    private final WebappLogMapper webappLogMapper;

    @Override
    public Page<WebappLogResponse> getByPage(Page<WebappLogResponse> page, WebappQueryRequest request) {
        return MybatisPageUtil.fromMybatis(webappLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





