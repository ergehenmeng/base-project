package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.log.ManageQueryRequest;
import com.eghm.mapper.ManageLogMapper;
import com.eghm.service.sys.ManageLogQueryGateway;
import com.eghm.vo.operate.log.ManageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisManageLogQueryGateway implements ManageLogQueryGateway {

    private final ManageLogMapper manageLogMapper;

    @Override
    public Page<ManageLogResponse> getByPage(Page<ManageLogResponse> page, ManageQueryRequest request) {
        return MybatisPageUtil.fromMybatis(manageLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





