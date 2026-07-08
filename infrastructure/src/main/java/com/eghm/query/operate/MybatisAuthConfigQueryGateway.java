package com.eghm.query.operate;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.AuthConfigMapper;
import com.eghm.service.operate.AuthConfigQueryGateway;
import com.eghm.vo.operate.auth.AuthConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisAuthConfigQueryGateway implements AuthConfigQueryGateway {

    private final AuthConfigMapper authConfigMapper;

    @Override
    public Page<AuthConfigResponse> getByPage(PagingQuery request) {
        return MybatisPageUtil.fromMybatis(authConfigMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request.getQueryName()));
    }
}

