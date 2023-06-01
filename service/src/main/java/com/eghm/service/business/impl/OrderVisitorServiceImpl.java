package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ProductType;
import com.eghm.enums.ref.VisitorState;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.OrderVisitorMapper;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.service.business.handler.dto.VisitorDTO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 二哥很猛
 * @date 2022/7/27
 */
@Service("orderVisitorService")
@AllArgsConstructor
@Slf4j
public class OrderVisitorServiceImpl implements OrderVisitorService {

    private final OrderVisitorMapper orderVisitorMapper;

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
    public void lockVisitor(ProductType productType, String orderNo, Long refundId, List<Long> visitorList) {
        if (CollUtil.isEmpty(visitorList)) {
            log.info("退款锁定用户为空,可能是非实名制用户 [{}] [{}] [{}]", orderNo, refundId, productType);
            return;
        }
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getProductType, productType);
        wrapper.in(OrderVisitor::getId, visitorList);
        wrapper.eq(OrderVisitor::getState, VisitorState.UN_PAY);
        wrapper.set(OrderVisitor::getState, VisitorState.REFUND);
        wrapper.set(OrderVisitor::getCollectId, refundId);
        int update = orderVisitorMapper.update(null, wrapper);
        // 退款锁定游客信息时,该游客一定是未核销的, 因此正常情况下更新的数量一定和visitorList数量一致的
        // 除非用户自己选择游客信息存在已核销的用户
        if (visitorList.size() != update) {
            log.error("退款人可能存在部分被核销的订单信息 [{}] [{}] [{}] [{}]", orderNo, refundId, visitorList, update);
            throw new BusinessException(ErrorCode.VISITOR_STATE_ERROR);
        }
    }

    @Override
    public void unlockVisitor(String orderNo, Long refundId) {
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getCollectId, refundId);
        wrapper.eq(OrderVisitor::getState, VisitorState.REFUND);
        wrapper.set(OrderVisitor::getState, VisitorState.UN_PAY);
        orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public void updateVisitor(String orderNo, VisitorState state) {
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.set(OrderVisitor::getState, state);
        orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public int visitorVerify(String orderNo, List<Long> visitorList, long visitorId) {
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
            String unPaid = selectList.stream().filter(orderVisitor -> orderVisitor.getState() != VisitorState.PAID).map(OrderVisitor::getUserName).collect(Collectors.joining(","));
            if (StrUtil.isNotBlank(unPaid)) {
                log.error("用户[{}]不是待使用状态,无法进行核销 orderNo:[{}] [{}]", unPaid, orderNo, visitorList);
                throw new BusinessException(ErrorCode.VISITOR_VERIFY_ERROR.getCode(), String.format(ErrorCode.VISITOR_VERIFY_ERROR.getMsg(), unPaid));
            }
        }
        LambdaUpdateWrapper<OrderVisitor> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.in(CollUtil.isNotEmpty(visitorList), OrderVisitor::getId, visitorList);
        wrapper.eq(OrderVisitor::getState, VisitorState.PAID);
        wrapper.set(OrderVisitor::getState, VisitorState.USED);
        wrapper.set(OrderVisitor::getCollectId, visitorId);
        return orderVisitorMapper.update(null, wrapper);
    }

    @Override
    public long getUnVerify(String orderNo) {
        LambdaQueryWrapper<OrderVisitor> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OrderVisitor::getOrderNo, orderNo);
        wrapper.eq(OrderVisitor::getState, VisitorState.PAID);
        return orderVisitorMapper.selectCount(wrapper);
    }
}
