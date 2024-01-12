package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.dto.business.restaurant.RestaurantQueryRequest;
import com.eghm.model.Restaurant;
import com.eghm.vo.business.restaurant.RestaurantVO;
import com.eghm.vo.business.restaurant.RestaurantResponse;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 餐饮商家信息表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-06-30
 */
public interface RestaurantMapper extends BaseMapper<Restaurant> {

    /**
     * 分页查询餐饮店列表
     *
     * @param page 分页
     * @param request 查询条件
     * @return 列表
     */
    Page<RestaurantResponse> listPage(Page<RestaurantResponse> page, @Param("param") RestaurantQueryRequest request);

    /**
     * 分页查询餐饮店列表
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<RestaurantVO> getByPage(Page<RestaurantVO> page, @Param("param") RestaurantQueryDTO dto);

    /**
     * 更新评分
     *
     * @param id id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 根据id查询列表
     * @param restaurantIds id列表
     * @return 列表
     */
    List<RestaurantVO> getList(@Param("restaurantIds") List<Long> restaurantIds);
}
