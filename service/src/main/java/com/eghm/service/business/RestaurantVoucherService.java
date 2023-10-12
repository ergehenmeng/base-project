package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.enums.ref.State;
import com.eghm.model.RestaurantVoucher;
import com.eghm.dto.business.restaurant.voucher.RestaurantVoucherAddRequest;
import com.eghm.dto.business.restaurant.voucher.RestaurantVoucherEditRequest;
import com.eghm.dto.business.restaurant.voucher.RestaurantVoucherQueryRequest;

/**
 * @author 二哥很猛
 * @date 2022/6/30
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
    void updateState(Long id, State state);

    /**
     * 主键查询餐饮券
     * @param id id
     * @return 餐饮券
     */
    RestaurantVoucher selectById(Long id);

    /**
     * 主键查询餐饮券,不存在抛异常
     * @param id id
     * @return 餐饮券
     */
    RestaurantVoucher selectByIdRequired(Long id);

    /**
     * 主键查询餐饮券,不是上架中的抛异常
     * @param id id
     * @return 餐饮券
     */
    RestaurantVoucher selectByIdShelve(Long id);

    /**
     * 更新库存信息
     * @param id id
     * @param num 负数-库存 正式+库存
     */
    void updateStock(Long id, Integer num);

    /**
     * 逻辑删除餐饮券
     * @param id id
     */
    void deleteById(Long id);
}
