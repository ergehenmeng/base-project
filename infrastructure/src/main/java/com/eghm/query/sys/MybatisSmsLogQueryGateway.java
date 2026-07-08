package com.eghm.query.sys;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.sys.log.SmsLogQueryRequest;
import com.eghm.mapper.SmsLogMapper;
import com.eghm.service.sys.SmsLogQueryGateway;
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





