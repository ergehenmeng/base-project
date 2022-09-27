package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import com.eghm.mapper.OrderVisitorMapper;
import com.eghm.model.OrderVisitor;
import com.eghm.model.dto.business.order.VisitorVO;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addVisitor(ProductType productType, String orderNo, List<VisitorVO> voList) {
        if (CollUtil.isEmpty(voList)) {
            log.info("该订单没有游客信息 [{}] [{}]", orderNo, productType);
            return;
        }
        for (VisitorVO vo : voList) {
            OrderVisitor visitor = DataUtil.copy(vo, OrderVisitor.class);
            visitor.setOrderNo(orderNo);
            visitor.setProductType(productType);
            visitor.setState(0);
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
        wrapper.eq(OrderVisitor::getState, 0);
        wrapper.set(OrderVisitor::getState, 2);
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
        wrapper.eq(OrderVisitor::getState, 2);
        wrapper.set(OrderVisitor::getState, 0);
        orderVisitorMapper.update(null, wrapper);
    }
}
