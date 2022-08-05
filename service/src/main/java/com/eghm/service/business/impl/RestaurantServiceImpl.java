package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.mapper.RestaurantMapper;
import com.eghm.dao.model.Restaurant;
import com.eghm.model.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.model.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.model.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.service.business.RestaurantService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
@Service("restaurantService")
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantMapper restaurantMapper;

    @Override
    public Page<Restaurant> getByPage(RestaurantQueryRequest request) {
        LambdaQueryWrapper<Restaurant> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, Restaurant::getState, request.getState());
        wrapper.like(StrUtil.isNotBlank(request.getQueryName()), Restaurant::getTitle, request.getQueryName());
        return restaurantMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void create(RestaurantAddRequest request) {
        // TODO 商家id
        Restaurant restaurant = DataUtil.copy(request, Restaurant.class);
        restaurantMapper.insert(restaurant);
    }

    @Override
    public void update(RestaurantEditRequest request) {
        Restaurant restaurant = DataUtil.copy(request, Restaurant.class);
        restaurantMapper.updateById(restaurant);
    }

    @Override
    public void updateState(Long id, State state) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getState, state);
        restaurantMapper.update(null, wrapper);
    }

    @Override
    public void updateAuditState(Long id, PlatformState state) {
        LambdaUpdateWrapper<Restaurant> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(Restaurant::getId, id);
        wrapper.set(Restaurant::getPlatformState, state);
        restaurantMapper.update(null, wrapper);
    }
}
