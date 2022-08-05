package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.dao.model.Restaurant;
import com.eghm.model.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.model.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.model.dto.business.restaurant.RestaurantQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/6/30
 */
public interface RestaurantService {

    /**
     * 分页查询商家信息
     * @param request 查询条件
     * @return 列表
     */
    Page<Restaurant> getByPage(RestaurantQueryRequest request);

    /**
     * 创建餐饮商家信息
     * @param request 餐饮店
     */
    void create(RestaurantAddRequest request);

    /**
     * 更新餐饮商家
     * @param request 餐饮店
     */
    void update(RestaurantEditRequest request);

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 更新审核状态
     * @param id 房型id
     * @param state 新状态
     */
    void updateAuditState(Long id, PlatformState state);
}
