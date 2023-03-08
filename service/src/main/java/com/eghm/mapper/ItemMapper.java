package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Item;
import com.eghm.model.dto.business.item.ItemCouponQueryDTO;
import com.eghm.model.dto.business.item.ItemQueryDTO;
import com.eghm.model.dto.business.item.ItemQueryRequest;
import com.eghm.model.vo.business.item.ItemListResponse;
import com.eghm.model.vo.business.item.ItemListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 零售商品信息 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-06
 */
public interface ItemMapper extends BaseMapper<Item> {
    
    /**
     * 分页查询商品信息
     * @param page 分页信息
     * @param request 查询条件
     * @return 商品列表
     */
    Page<ItemListResponse> listPage(Page<ItemListResponse> page, @Param("param") ItemQueryRequest request);
    
    /**
     * 查询店铺首页下推荐的商品列表, 不含下架的商品
     * @param shopId 店铺id
     * @param limit 查询多少条
     * @return 商品列表
     */
    List<ItemListVO> getPriorityItem(@Param("shopId") Long shopId, @Param("limit") Integer limit);
    
    /**
     * 查询店铺下推荐的商品列表, 不含下架的商品
     * @param limit 查询多少条
     * @return 商品列表
     */
    List<ItemListVO> getRecommendItem(@Param("limit") Integer limit);
    
    /**
     * 分页查询商品列表
     * @param page  分页信息
     * @param dto 查询条件
     * @return 商品信息
     */
    Page<ItemListVO> getByPage(Page<ItemListVO> page, @Param("param") ItemQueryDTO dto);
    
    /**
     * 优惠券所能使用的商品范围, 分页查询
     * @param page 分页信息
     * @param dto 查询条件
     * @return 列表
     */
    Page<ItemListVO> getCouponScopeByPage(Page<ItemListVO> page, @Param("param") ItemCouponQueryDTO dto);
}
