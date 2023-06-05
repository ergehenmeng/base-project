package com.eghm.service.business.impl;

import com.eghm.mapper.OrderVisitorRefundMapper;
import com.eghm.model.OrderVisitorRefund;
import com.eghm.service.business.OrderVisitorRefundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 游客退款记录关联表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-06-01
 */
@Service
@AllArgsConstructor
public class OrderVisitorRefundServiceImpl implements OrderVisitorRefundService {

    private final OrderVisitorRefundMapper orderVisitorRefundMapper;

    @Override
    public void insertVisitorRefund(String orderNo, Long refundId, List<Long> visitorList) {
        for (Long visitorId : visitorList) {
            OrderVisitorRefund refund = new OrderVisitorRefund();
            refund.setRefundId(refundId);
            refund.setOrderNo(orderNo);
            refund.setVisitorId(visitorId);
            orderVisitorRefundMapper.insert(refund);
        }
    }
}
