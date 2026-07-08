package com.eghm.query.pay;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.dto.business.pay.PayLogQueryRequest;
import com.eghm.mapper.PayNotifyLogMapper;
import com.eghm.pay.service.PayNotifyLogQueryGateway;
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





