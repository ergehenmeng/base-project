package com.eghm.service.business.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.security.SecurityHolder;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.venue.VenueOrderQueryDTO;
import com.eghm.dto.business.order.venue.VenueOrderQueryRequest;
import com.eghm.dto.ext.VenuePhase;
import com.eghm.mapper.VenueOrderMapper;
import com.eghm.model.VenueOrder;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.common.JsonService;
import com.eghm.service.sys.SysAreaService;
import com.eghm.utils.AssertUtil;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.venue.VenueOrderDetailResponse;
import com.eghm.vo.business.order.venue.VenueOrderDetailVO;
import com.eghm.vo.business.order.venue.VenueOrderResponse;
import com.eghm.vo.business.order.venue.VenueOrderVO;
import com.eghm.vo.business.venue.VenuePhaseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 场馆预约订单表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-04
 */
@Slf4j
@AllArgsConstructor
@Service("venueOrderService")
public class VenueOrderServiceImpl implements VenueOrderService {

    private final JsonService jsonService;

    private final SysAreaService sysAreaService;

    private final VenueOrderMapper venueOrderMapper;

    private final VenueSitePriceService venueSitePriceService;

    @Override
    public Page<VenueOrderResponse> listPage(VenueOrderQueryRequest request) {
        return venueOrderMapper.listPage(request.createPage(), request);
    }

    @Override
    public List<VenueOrderVO> getByPage(VenueOrderQueryDTO dto) {
        return venueOrderMapper.getList(dto.createPage(false), dto).getRecords();
    }

    @Override
    public void insert(VenueOrder venueOrder) {
        venueOrderMapper.insert(venueOrder);
    }

    @Override
    public VenueOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<VenueOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(VenueOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return venueOrderMapper.selectOne(wrapper);
    }

    @Override
    public void updateStock(String orderNo, Integer num) {
        VenueOrder venueOrder = this.getByOrderNo(orderNo);
        List<VenuePhase> phaseList = jsonService.fromJsonList(venueOrder.getTimePhase(), VenuePhase.class);
        List<Long> ids = phaseList.stream().map(VenuePhase::getId).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(ids)) {
            venueSitePriceService.updateStock(ids, num);
        } else {
            log.error("订单库存更新为空 [{}]", orderNo);
        }
    }

    @Override
    public VenueOrderDetailResponse detail(String orderNo) {
        Long merchantId = SecurityHolder.getMerchantId();
        VenueOrderDetailResponse detail = venueOrderMapper.detail(orderNo, merchantId);
        AssertUtil.assertOrderNotNull(detail, orderNo, merchantId);
        detail.setPhaseList(jsonService.fromJsonList(detail.getTimePhase(), VenuePhaseVO.class));
        return detail;
    }

    @Override
    public VenueOrderDetailVO getDetail(String orderNo, Long memberId) {
        VenueOrderDetailVO detail = venueOrderMapper.getDetail(orderNo, memberId);
        AssertUtil.assertOrderNotNull(detail, orderNo, memberId);
        detail.setDetailAddress(sysAreaService.parseArea(detail.getCityId(), detail.getCountyId()) + detail.getDetailAddress());
        detail.setPhaseList(jsonService.fromJsonList(detail.getTimePhase(), VenuePhaseVO.class));
        return detail;
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return venueOrderMapper.getSnapshot(orderId, orderNo);
    }
}
