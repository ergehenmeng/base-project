package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.sys.log.ManageQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.ManageLogMapper;
import com.eghm.application.system.query.ManageLogQueryService;
import com.eghm.application.shared.vo.operate.log.ManageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisManageLogQueryService implements ManageLogQueryService {

    private final ManageLogMapper manageLogMapper;

    @Override
    public Page<ManageLogResponse> getByPage(Page<ManageLogResponse> page, ManageQueryRequest request) {
        return MybatisPageUtil.fromMybatis(manageLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





