package com.eghm.infrastructure.persistence.mybatis.system.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.sys.log.SmsLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.SmsLogMapper;
import com.eghm.application.system.service.SmsLogQueryGateway;
import com.eghm.vo.operate.log.SmsLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisSmsLogQueryGateway implements SmsLogQueryGateway {

    private final SmsLogMapper smsLogMapper;

    @Override
    public Page<SmsLogResponse> getByPage(Page<SmsLogResponse> page, SmsLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(smsLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





