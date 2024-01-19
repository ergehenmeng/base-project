package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryRequest;
import com.eghm.mapper.VoucherOrderMapper;
import com.eghm.model.VoucherOrder;
import com.eghm.service.business.VoucherOrderService;
import com.eghm.utils.AssertUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.restaurant.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/23
 */
@Service("voucherOrderService")
@AllArgsConstructor
@Slf4j
public class VoucherOrderServiceImpl implements VoucherOrderService {

    private final VoucherOrderMapper voucherOrderMapper;

    @Override
    public Page<VoucherOrderResponse> listPage(VoucherOrderQueryRequest request) {
        return voucherOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<VoucherOrderResponse> getList(VoucherOrderQueryRequest request) {
        Page<VoucherOrderResponse> responsePage = voucherOrderMapper.listPage(request.createNullPage(), request);
        return responsePage.getRecords();
    }

    @Override
    public List<VoucherOrderVO> getByPage(VoucherOrderQueryDTO dto) {
        Page<VoucherOrderVO> voPage = voucherOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public void insert(VoucherOrder order) {
        voucherOrderMapper.insert(order);
    }

    @Override
    public VoucherOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<VoucherOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VoucherOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return voucherOrderMapper.selectOne(wrapper);
    }

    @Override
    public VoucherOrder selectById(Long id) {
        return voucherOrderMapper.selectById(id);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return voucherOrderMapper.getSnapshot(orderId, orderNo);
    }

    @Override
    public VoucherOrderDetailVO getDetail(String orderNo, Long memberId) {
        VoucherOrderDetailVO detail = voucherOrderMapper.getDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        return detail;
    }

    @Override
    public VoucherOrderDetailResponse detail(String orderNo) {
        Long merchantId = SecurityHolder.getMerchantId();
        VoucherOrderDetailResponse detail = voucherOrderMapper.detail(orderNo, merchantId);
        AssertUtil.assertOrderNotNull(detail, orderNo, merchantId);
        return detail;
    }

    @Override
    public VoucherOrderSnapshotVO snapshotDetail(String orderNo, Long memberId) {
        VoucherOrderSnapshotVO detail = voucherOrderMapper.snapshotDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        return detail;
    }
}
