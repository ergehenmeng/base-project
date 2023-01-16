package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Restaurant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.model.dto.business.homestay.HomestayQueryDTO;
import com.eghm.model.dto.business.restaurant.RestaurantQueryDTO;
import com.eghm.model.vo.business.homestay.HomestayListVO;
import com.eghm.model.vo.business.restaurant.RestaurantListVO;
import org.apache.ibatis.annotations.Param;

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
     * @param page 分页
     * @param dto 查询条件
     * @return 列表
     */
    Page<RestaurantListVO> getByPage(Page<RestaurantListVO> page, @Param("param") RestaurantQueryDTO dto);
}
