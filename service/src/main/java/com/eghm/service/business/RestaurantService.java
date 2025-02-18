package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseStoreQueryRequest;
import com.eghm.dto.business.restaurant.*;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.State;
import com.eghm.model.Restaurant;
import com.eghm.vo.business.base.BaseStoreResponse;
import com.eghm.vo.business.restaurant.BaseRestaurantResponse;
import com.eghm.vo.business.restaurant.RestaurantDetailVO;
import com.eghm.vo.business.restaurant.RestaurantResponse;
import com.eghm.vo.business.restaurant.RestaurantVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/6/30
 */
public interface RestaurantService {

    /**
     * 分页查询商家信息
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<RestaurantResponse> getByPage(RestaurantQueryRequest request);

    /**
     * 分页查询商家信息 导出使用
     *
     * @param request 查询条件
     * @return 列表
     */
    List<RestaurantResponse> getList(RestaurantQueryRequest request);

    /**
     * 根据商户id查询商家信息
     *
     * @param merchantId 商户id
     * @return 商家信息
     */
    List<BaseRestaurantResponse> getList(Long merchantId);

    /**
     * 创建餐饮商家信息
     *
     * @param request 餐饮店
     */
    void create(RestaurantAddRequest request);

    /**
     * 更新餐饮商家
     *
     * @param request 餐饮店
     */
    void update(RestaurantEditRequest request);

    /**
     * 更新上下架状态
     *
     * @param id    id
     * @param state 状态
     */
    void updateState(Long id, State state);

    /**
     * 主键查询餐饮商家
     *
     * @param id id
     * @return 商家信息
     */
    Restaurant selectByIdRequired(Long id);

    /**
     * 主键查询餐饮商家,未上架直接抛异常
     *
     * @param id id
     * @return 商家信息
     */
    Restaurant selectByIdShelve(Long id);

    /**
     * 分页查询餐饮店铺
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<RestaurantVO> getByPage(RestaurantQueryDTO dto);

    /**
     * 查询餐饮店详情
     *
     * @param dto id
     * @return 详细信息
     */
    RestaurantDetailVO detailById(RestaurantDTO dto);

    /**
     * 逻辑删除 (只有平台用户能操作)
     *
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新商品分数
     *
     * @param vo 店铺和商品信息
     */
    void updateScore(CalcStatistics vo);

    /**
     * 分页查询列表(含商户信息)
     *
     * @param request 查询条件
     * @return 列表
     */
    Page<BaseStoreResponse> getStorePage(BaseStoreQueryRequest request);

    /**
     * 注销商户的餐饮店铺
     *
     * @param merchantId 商户ID
     */
    void logout(Long merchantId);
}
