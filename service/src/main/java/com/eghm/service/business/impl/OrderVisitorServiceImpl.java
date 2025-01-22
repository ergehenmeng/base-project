package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.OrderState;
import com.eghm.enums.ProductType;
import com.eghm.enums.VisitorState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderVisitorMapper;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.OrderVisitorRefundService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.state.machine.dto.VisitorDTO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eghm.utils.StringUtil.isNotBlank;

/**
 * @author 二哥很猛
 * @since 2022/7/27
 */
@Service("orderVisitorService")
@AllArgsConstructor
@Slf4j
public class OrderVisitorServiceImpl implements OrderVisitorService {

    private final OrderVisitorMapper orderVisitorMapper;

    private final OrderVisitorRefundService orderVisitorRefundService;

    @Override
    public void addVisitor(ProductType productType, String orderNo, List<VisitorDTO> voList) {
        if (CollUtil.isEmpty(voList)) {
            log.info("该订单没有游客信息 [{}] [{}]", orderNo, productType);
            return;
        }
        for (VisitorDTO vo : voList) {
            OrderVisitor visitor = DataUtil.copy(vo, OrderVisitor.class);
            visitor.setOrderNo(orderNo);
            visitor.setProductType(productType);
            visitor.setState(VisitorState.UN_PAY);
            orderVisitorMapper.insert(visitor);
        }
    }

    @Override
    public List<OrderVisitor> getByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        return orderVisitorMapper.selectList(wrapper);
    }

    @Override
    public void refundLock(ProductType productType, String orderNo, Long refundId, List<Long> visitorList, VisitorState target, VisitorState... source) {
        if (CollUtil.isEmpty(visitorList)) {
            log.info("退款锁定用户为空,可能是非实名制用户 [{}] [{}] [{}]", orderNo, refundId, productType);
            return;
        }
        orderVisitorRefundService.insertVisitorRefund(orderNo, refundId, visitorList);
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getProductType, productType);
        wrapper.in(OrderVisitor::getId, visitorList);
        wrapper.in(OrderVisitor::getState, (Object[]) source);
        wrapper.set(OrderVisitor::getState, target);
        int update = orderVisitorMapper.update(null, wrapper);
        // 退款锁定游客信息时,该游客一定是未核销的, 因此正常情况下更新的数量一定和visitorList数量一致的
        // 除非用户自己选择游客信息存在已核销的用户
        if (visitorList.size() != update) {
            log.error("退款人可能存在部分被核销的订单信息 [{}] [{}] [{}] [{}]", orderNo, refundId, visitorList, update);
            throw new BusinessException(ErrorCode.VISITOR_STATE_ERROR);
        }
    }

    @Override
    public void updateVisitor(String orderNo, VisitorState state) {
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.set(OrderVisitor::getState, state);
        orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public void refundVisitor(String orderNo, Long refundId, VisitorState state) {
        orderVisitorMapper.refundVisitor(orderNo, refundId, state);
    }

    @Override
    public int visitorVerify(String orderNo, List<Long> visitorList, long verifyId) {
        // 如果是部分核销需要判断前端传递过来的核销信息是否合法
        if (CollUtil.isNotEmpty(visitorList)) {
            LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(OrderVisitor::getOrderNo, orderNo);
            wrapper.in(OrderVisitor::getId, visitorList);
            List<OrderVisitor> selectList = orderVisitorMapper.selectList(wrapper);
            if (selectList.size() != visitorList.size()) {
                log.error("核销信息数量不匹配 [{}] [{}]", orderNo, visitorList);
                throw new BusinessException(ErrorCode.VERIFY_EXPIRE_ERROR);
            }
            String unPaid = selectList.stream().filter(orderVisitor -> orderVisitor.getState() != VisitorState.PAID).map(OrderVisitor::getMemberName).collect(Collectors.joining(","));
            if (isNotBlank(unPaid)) {
                log.error("用户[{}]不是待使用状态,无法进行核销 orderNo:[{}] [{}]", unPaid, orderNo, visitorList);
                throw new BusinessException(ErrorCode.VISITOR_VERIFY_ERROR, unPaid);
            }
        }
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.in(CollUtil.isNotEmpty(visitorList), OrderVisitor::getId, visitorList);
        wrapper.eq(OrderVisitor::getState, VisitorState.PAID);
        wrapper.set(OrderVisitor::getState, VisitorState.USED);
        wrapper.set(OrderVisitor::getVerifyId, verifyId);
        return orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public long getUnVerify(String orderNo) {
        LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getState, VisitorState.PAID);
        return orderVisitorMapper.selectCount(wrapper);
    }

    @Override
    public long getVerify(String orderNo) {
        LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getState, VisitorState.USED);
        return orderVisitorMapper.selectCount(wrapper);
    }

    @Override
    public List<OrderVisitor> getByIds(List<Long> ids, String orderNo) {
        LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
        wrapper.in(OrderVisitor::getId, ids);
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        return orderVisitorMapper.selectList(wrapper);
    }

    @Override
    public void updateRefund(List<Long> ids, String orderNo) {
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.in(OrderVisitor::getId, ids);
        wrapper.set(OrderVisitor::getState, VisitorState.REFUND);
        orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public OrderState getOrderState(String orderNo) {
        List<OrderVisitor> visitorList = this.getByOrderNo(orderNo);
        return this.getOrderState(visitorList);
    }

    /**
     * 根据游客信息计算主订单的状态
     *
     * @param visitorList 游客信息
     * @return 主订单状态
     */
    private OrderState getOrderState(List<OrderVisitor> visitorList) {
        Optional<OrderVisitor> optional = visitorList.stream().filter(orderVisitor -> orderVisitor.getState() == VisitorState.PAID).findFirst();
        if (optional.isPresent()) {
            return OrderState.UN_USED;
        }
        Optional<OrderVisitor> useOptional = visitorList.stream().filter(orderVisitor -> orderVisitor.getState() == VisitorState.USED).findFirst();
        Optional<OrderVisitor> refundingOptional = visitorList.stream().filter(orderVisitor -> orderVisitor.getState() == VisitorState.REFUNDING).findFirst();
        // 存在已使用和退款中,退款完成之前默认是待使用
        if (useOptional.isPresent() && refundingOptional.isPresent()) {
            return OrderState.UN_USED;
        }
        Optional<OrderVisitor> refundOptional = visitorList.stream().filter(orderVisitor -> orderVisitor.getState() == VisitorState.REFUND).findFirst();
        // 表示订单只有已使用和已退款
        if (useOptional.isPresent() && refundOptional.isPresent()) {
            return OrderState.COMPLETE;
        }
        // 存在存款中的游客信息
        if (refundingOptional.isPresent()) {
            return OrderState.REFUND;
        }
        // 全部退款完成
        return OrderState.CLOSE;
    }
}
