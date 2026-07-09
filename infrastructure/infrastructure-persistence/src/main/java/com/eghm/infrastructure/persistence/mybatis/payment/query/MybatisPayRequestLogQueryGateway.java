package com.eghm.infrastructure.persistence.mybatis.payment.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.pay.PayLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.PayRequestLogMapper;
import com.eghm.application.payment.port.out.PayRequestLogQueryGateway;
import com.eghm.application.shared.vo.operate.log.PayRequestLogResponse;
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





