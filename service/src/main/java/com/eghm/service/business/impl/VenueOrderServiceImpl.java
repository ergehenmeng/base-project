package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.ext.VenuePhase;
import com.eghm.mapper.VenueOrderMapper;
import com.eghm.model.VenueOrder;
import com.eghm.service.business.VenueOrderService;
import com.eghm.service.business.VenueSitePriceService;
import com.eghm.service.common.JsonService;
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

    private final VenueOrderMapper venueOrderMapper;

    private final VenueSitePriceService venueSitePriceService;

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
        venueSitePriceService.updateStock(ids, num);
    }
}
