package com.eghm.infrastructure.persistence.mybatis.payment.query;

import com.eghm.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.PayNotifyLogMapper;
import com.eghm.application.payment.service.PayNotifyLogQueryGateway;
import com.eghm.vo.operate.log.PayNotifyLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisPayNotifyLogQueryGateway implements PayNotifyLogQueryGateway {

    private final PayNotifyLogMapper payNotifyLogMapper;

    @Override
    public Page<PayNotifyLogResponse> getByPage(Page<PayNotifyLogResponse> page, PayLogQueryRequest request) {
        return MybatisPageUtil.fromMybatis(payNotifyLogMapper.getByPage(MybatisPageUtil.toMybatis(page), request));
    }
}





