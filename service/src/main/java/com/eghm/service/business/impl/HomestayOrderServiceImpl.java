package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryDTO;
import com.eghm.dto.business.order.homestay.HomestayOrderQueryRequest;
import com.eghm.mapper.HomestayOrderMapper;
import com.eghm.model.HomestayOrder;
import com.eghm.model.OrderVisitor;
import com.eghm.service.business.HomestayOrderService;
import com.eghm.service.business.OrderService;
import com.eghm.service.business.OrderVisitorService;
import com.eghm.utils.AssertUtil;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.VisitorVO;
import com.eghm.vo.business.order.homestay.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/17
 */
@Slf4j
@Service("homestayOrderService")
@AllArgsConstructor
public class HomestayOrderServiceImpl implements HomestayOrderService {

    private final OrderService orderService;

    private final OrderVisitorService orderVisitorService;

    private final HomestayOrderMapper homestayOrderMapper;

    @Override
    public Page<HomestayOrderResponse> listPage(HomestayOrderQueryRequest request) {
        return homestayOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<HomestayOrderResponse> getList(HomestayOrderQueryRequest request) {
        Page<HomestayOrderResponse> listPage = homestayOrderMapper.listPage(request.createNullPage(), request);
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
    public HomestayOrderSnapshotVO snapshotDetail(String orderNo, Long memberId) {
        HomestayOrderSnapshotVO snapshot = homestayOrderMapper.snapshotDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(snapshot, orderNo, memberId);
        return snapshot;
    }
}
