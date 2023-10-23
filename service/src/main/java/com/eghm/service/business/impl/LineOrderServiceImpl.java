package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.line.LineOrderQueryDTO;
import com.eghm.mapper.LineOrderMapper;
import com.eghm.model.LineOrder;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.LineOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.VisitorVO;
import com.eghm.vo.business.order.line.LineOrderDetailVO;
import com.eghm.vo.business.order.line.LineOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/9/1
 */
@Service("lineOrderService")
@AllArgsConstructor
@Slf4j
public class LineOrderServiceImpl implements LineOrderService {

    private final LineOrderMapper lineOrderMapper;

    private final OrderVisitorService orderVisitorService;

    private final OrderService orderService;

    @Override
    public List<LineOrderVO> getByPage(LineOrderQueryDTO dto) {
        Page<LineOrderVO> voPage = lineOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public void insert(LineOrder order) {
        lineOrderMapper.insert(order);
    }

    @Override
    public LineOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<LineOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LineOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return lineOrderMapper.selectOne(wrapper);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return lineOrderMapper.getSnapshot(orderId, orderNo);
    }

    @Override
    public LineOrderDetailVO getDetail(String orderNo, Long memberId) {
        LineOrderDetailVO detail = lineOrderMapper.getDetail(orderNo, memberId);
        List<OrderVisitor> visitorList = orderVisitorService.getByOrderNo(orderNo);
        detail.setVisitorList(DataUtil.copy(visitorList, VisitorVO.class));
        detail.setVerifyCode(orderService.encryptVerifyNo(orderNo));
        return detail;
    }
}
