package com.eghm.infrastructure.persistence.mybatis.payment.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.PayRequestLogMapper;
import com.eghm.application.payment.service.PayRequestLogQueryGateway;
import com.eghm.vo.operate.log.PayRequestLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisPayRequestLogQueryGateway implements PayRequestLogQueryGateway {

    private final PayRequestLogMapper payRequestLogMapper;

    @Override
    public Page<PayRequestLogResponse> getByPage(Page<PayRequestLogResponse> page, PayLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(payRequestLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





