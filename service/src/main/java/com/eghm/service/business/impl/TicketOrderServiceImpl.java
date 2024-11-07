package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.mapper.TicketOrderMapper;
import com.eghm.model.OrderVisitor;
import com.eghm.model.TicketOrder;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.TicketOrderService;
import com.eghm.utils.AssertUtil;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.VisitorVO;
import com.eghm.vo.business.order.ticket.TicketOrderDetailResponse;
import com.eghm.vo.business.order.ticket.TicketOrderDetailVO;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import com.eghm.vo.business.order.ticket.TicketOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
@Service("ticketOrderService")
@AllArgsConstructor
@Slf4j
public class TicketOrderServiceImpl implements TicketOrderService {

    private final OrderService orderService;

    private final TicketOrderMapper ticketOrderMapper;

    private final OrderVisitorService orderVisitorService;

    @Override
    public Page<TicketOrderResponse> getByPage(TicketOrderQueryRequest request) {
        return ticketOrderMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<TicketOrderResponse> getList(TicketOrderQueryRequest request) {
        Page<TicketOrderResponse> responsePage = ticketOrderMapper.getByPage(request.createNullPage(), request);
        return responsePage.getRecords();
    }

    @Override
    public void insert(TicketOrder order) {
        ticketOrderMapper.insert(order);
    }

    @Override
    public TicketOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<TicketOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(TicketOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return ticketOrderMapper.selectOne(wrapper);
    }

    @Override
    public List<TicketOrderVO> getByPage(TicketOrderQueryDTO dto) {
        Page<TicketOrderVO> voPage = ticketOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public TicketOrderDetailVO getDetail(String orderNo, Long memberId) {
        TicketOrderDetailVO detail = ticketOrderMapper.getDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(orderNo);
        detail.setVisitorList(DataUtil.copy(visitorList, VisitorVO.class));
        detail.setVerifyNo(orderService.encryptVerifyNo(detail.getVerifyNo()));
        return detail;
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return ticketOrderMapper.getSnapshot(orderId, orderNo);
    }

    @Override
    public TicketOrderDetailResponse detail(String orderNo) {
        Long merchantId = SecurityHolder.getMerchantId();
        TicketOrderDetailResponse detail = ticketOrderMapper.detail(orderNo, merchantId);
        AssertUtil.assertOrderNotNull(detail, orderNo, merchantId);
        if (Boolean.TRUE.equals(detail.getRealBuy())) {
            List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(orderNo);
            detail.setVisitorList(DataUtil.copy(visitorList, VisitorVO.class));
        }
        return detail;
    }
}
