package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Item;
import com.eghm.dto.business.item.ItemCouponQueryDTO;
import com.eghm.dto.business.item.ItemQueryDTO;
import com.eghm.dto.business.item.ItemQueryRequest;
import com.eghm.vo.business.item.ItemListResponse;
import com.eghm.vo.business.item.ItemListVO;
import com.eghm.vo.business.item.ItemVO;
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
    
    /**
     * 更新销售量
     * @param id 商品ID
     * @param num 销售数量
     */
    void updateSaleNum(@Param("id") Long id, @Param("num") Integer num);
    
    /**
     * 根据该订单编号更新该订单下所有商品的销售量
     * @param orderNo 订单编号
     */
    void updateSaleNumByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询商品详细信息
     * @param id id
     * @return 商品详细信息
     */
    ItemVO detailById(@Param("id") Long id);
}
