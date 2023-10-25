package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.order.restaurant.VoucherOrderQueryDTO;
import com.eghm.mapper.RestaurantOrderMapper;
import com.eghm.model.RestaurantOrder;
import com.eghm.service.business.RestaurantOrderService;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderDetailVO;
import com.eghm.vo.business.order.restaurant.RestaurantOrderVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/23
 */
@Service("restaurantOrderService")
@AllArgsConstructor
@Slf4j
public class RestaurantOrderServiceImpl implements RestaurantOrderService {

    private final RestaurantOrderMapper restaurantOrderMapper;

    @Override
    public List<RestaurantOrderVO> getByPage(VoucherOrderQueryDTO dto) {
        Page<RestaurantOrderVO> voPage = restaurantOrderMapper.getList(dto.createPage(false), dto);
        return voPage.getRecords();
    }

    @Override
    public void insert(RestaurantOrder order) {
        restaurantOrderMapper.insert(order);
    }

    @Override
    public RestaurantOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<RestaurantOrder> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RestaurantOrder::getOrderNo, orderNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return restaurantOrderMapper.selectOne(wrapper);
    }

    @Override
    public RestaurantOrder selectById(Long id) {
        return restaurantOrderMapper.selectById(id);
    }

    @Override
    public ProductSnapshotVO getSnapshot(Long orderId, String orderNo) {
        return restaurantOrderMapper.getSnapshot(orderId, orderNo);
    }

    @Override
    public RestaurantOrderDetailVO getDetail(String orderNo, Long memberId) {
        return restaurantOrderMapper.getDetail(orderNo, memberId);
    }
}
