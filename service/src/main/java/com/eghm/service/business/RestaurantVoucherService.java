package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dao.model.RestaurantVoucher;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.model.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;

/**
 * @author wyb
 * @date 2022/6/30 22:01
 */
public interface RestaurantVoucherService {

    /**
     * 分页查询餐饮券信息
     * @param request 查询条件
     * @return 列表
     */
    Page<RestaurantVoucher> getByPage(RestaurantVoucherQueryRequest request);

    /**
     * 创建餐饮券
     * @param request 餐饮券信息
     */
    void create(RestaurantVoucherAddRequest request);

    /**
     * 更新餐饮券
     * @param request 餐饮券信息
     */
    void update(RestaurantVoucherEditRequest request);

    /**
     * 更新上下架状态
     * @param id id
     * @param state 状态
     */
    void updateState(Long id, Integer state);
}
