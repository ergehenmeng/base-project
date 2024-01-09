package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.homestay.HomestayOrderConfirmRequest;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.SmsType;
import com.eghm.enums.event.impl.HomestayEvent;
import com.eghm.enums.ref.ConfirmState;
import com.eghm.enums.ref.ProductType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.HomestayOrderMapper;
import com.eghm.model.HomestayOrder;
import com.eghm.model.Order;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.context.RefundApplyContext;
import com.eghm.service.common.SmsService;
import com.eghm.state.machine.StateHandler;
import com.eghm.utils.AssertUtil;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.VisitorVO;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderDetailVO;
import com.eghm.vo.business.order.homestay.HomestayOrderResponse;
import com.eghm.vo.business.order.homestay.HomestayOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
@Slf4j
@Service("homestayOrderService")
@AllArgsConstructor
public class HomestayOrderServiceImpl implements HomestayOrderService {

    private final HomestayOrderMapper homestayOrderMapper;

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    private final StateHandler stateHandler;

    private final SmsService smsService;
    @Override
    public Page<HomestayOrderResponse> listPage(HomestayOrderQueryRequest request) {
        return homestayOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<HomestayOrderResponse> getList(HomestayOrderQueryRequest request) {
        Page<HomestayOrderResponse> listPage = homestayOrderMapper.listPage(request.createPage(false), request);
        return listPage.getRecords();
    }

    @Override
    public List<HomestayOrderVO> getByPage(HomestayOrderQueryDTO dto) {
        Page<HomestayOrderVO> voPage = homestayOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public void insert(HomestayOrder order) {
        homestayOrderMapper.insert(order);
    }

    @Override
    public HomestayOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<HomestayOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HomestayOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return homestayOrderMapper.selectOne(wrapper);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return homestayOrderMapper.getSnapshot(orderId, orderNo);
    }

    @Override
    public HomestayOrderDetailVO getDetail(String orderNo, Long memberId) {
        HomestayOrderDetailVO detail = homestayOrderMapper.getDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        detail.setVerifyNo(orderService.encryptVerifyNo(detail.getVerifyNo()));
        List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(orderNo);
        detail.setVisitorList(DataUtil.copy(visitorList, VisitorVO.class));
        return detail;
    }

    @Override
    public HomestayOrderDetailResponse detail(String orderNo) {
        Long merchantId = SecurityHolder.getMerchantId();
        HomestayOrderDetailResponse detail = homestayOrderMapper.detail(orderNo, merchantId);
        AssertUtil.assertOrderNotNull(detail, orderNo, merchantId);
        List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(orderNo);
        detail.setVisitorList(DataUtil.copy(visitorList, VisitorVO.class));
        return detail;
    }

    @Override
    public void confirm(HomestayOrderConfirmRequest request) {
        HomestayOrder homestayOrder = this.getByOrderNo(request.getOrderNo());
        if (homestayOrder.getConfirmState() != ConfirmState.WAIT_CONFIRM) {
            log.warn("该民宿订单已确认 [{}] [{}]", request.getOrderNo(), homestayOrder.getConfirmState());
            throw new BusinessException(ErrorCode.ORDER_CONFIRM);
        }
        homestayOrder.setConfirmState(request.getConfirmState());
        homestayOrder.setRemark(request.getRemark());
        homestayOrderMapper.updateById(homestayOrder);

        if (request.getConfirmState() == ConfirmState.FAIL_CONFIRM) {
            log.info("订单:[{}],确认无房开始执行退款逻辑", request.getOrderNo());
            Order order = orderService.getByOrderNo(request.getOrderNo());
            List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(request.getOrderNo());
            List<Long> visitorIds = visitorList.stream().map(OrderVisitor::getId).collect(Collectors.toList());
            RefundApplyContext context = new RefundApplyContext();
            context.setMemberId(order.getMemberId());
            context.setNum(visitorIds.size());
            context.setVisitorIds(visitorIds);
            context.setApplyType(1);
            context.setReason(request.getRemark());
            context.setApplyAmount(order.getPayAmount());
            context.setOrderNo(request.getOrderNo());
            stateHandler.fireEvent(ProductType.HOMESTAY, order.getState().getValue(), HomestayEvent.CONFIRM_ROOM, context);
            // 发送短信通知
            smsService.sendSms(SmsType.CONFIRM_NO_ROOM, order.getMobile(), request.getOrderNo());
        }
    }
}
