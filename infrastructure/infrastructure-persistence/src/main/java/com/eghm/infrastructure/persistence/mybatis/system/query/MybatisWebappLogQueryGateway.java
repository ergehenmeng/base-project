package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.sys.log.WebappQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.WebappLogMapper;
import com.eghm.application.system.service.WebappLogQueryGateway;
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





