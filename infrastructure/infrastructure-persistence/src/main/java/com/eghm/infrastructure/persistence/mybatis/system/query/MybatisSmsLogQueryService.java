package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.sys.log.SmsLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SmsLogMapper;
import com.eghm.application.system.query.SmsLogQueryService;
import com.eghm.application.shared.vo.operate.log.SmsLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSmsLogQueryService implements SmsLogQueryService {

    private final SmsLogMapper smsLogMapper;

    @Override
    public Page<SmsLogResponse> getByPage(Page<SmsLogResponse> page, SmsLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(smsLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





