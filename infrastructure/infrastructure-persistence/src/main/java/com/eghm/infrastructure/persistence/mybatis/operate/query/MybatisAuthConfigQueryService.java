package com.eghm.infrastructure.persistence.mybatis.operate.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.infrastructure.persistence.mybatis.mapper.AuthConfigMapper;
import com.eghm.application.operate.query.AuthConfigQueryService;
import com.eghm.application.shared.vo.operate.auth.AuthConfigResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisAuthConfigQueryService implements AuthConfigQueryService {

    private final AuthConfigMapper authConfigMapper;

    @Override
    public Page<AuthConfigResponse> getByPage(PagingQuery request) {
        return MybatisPageUtil.fromMybatis(authConfigMapper.getByPage(MybatisPageUtil.toMybatis(request.createPage()), request.getQueryName()));
    }
}

