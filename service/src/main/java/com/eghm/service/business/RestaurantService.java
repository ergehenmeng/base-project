package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.restaurant.RestaurantAddRequest;
import com.eghm.dto.business.restaurant.RestaurantEditRequest;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.dto.ext.CalcStatistics;
import com.eghm.enums.ref.State;
import com.eghm.model.Restaurant;
import com.eghm.vo.business.restaurant.RestaurantListVO;
import com.eghm.vo.business.restaurant.RestaurantVO;

import java.util.List;

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
     * 主键查询餐饮商家
     * @param id id
     * @return 商家信息
     */
    Restaurant selectByIdRequired(Long id);

    /**
     * 主键查询餐饮商家,未上架直接抛异常
     * @param id id
     * @return 商家信息
     */
    Restaurant selectByIdShelve(Long id);

    /**
     * 分页查询餐饮店铺
     * @param dto 查询条件
     * @return 列表
     */
    List<RestaurantListVO> getByPage(RestaurantQueryDTO dto);

    /**
     * 查询餐饮店详情
     * @param id id
     * @return 详细信息
     */
    RestaurantVO detailById(Long id);
    
    /**
     * 逻辑删除
     * @param id id
     */
    void deleteById(Long id);

    /**
     * 更新商品分数
     * @param vo 店铺和商品信息
     */
    void updateScore(CalcStatistics vo);
}
