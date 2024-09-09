package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.base.BaseProductQueryRequest;
import com.eghm.dto.business.coupon.product.ItemCouponQueryDTO;
import com.eghm.dto.business.item.ItemQueryDTO;
import com.eghm.dto.business.item.ItemQueryRequest;
import com.eghm.dto.statistics.ProductRequest;
import com.eghm.model.Item;
import com.eghm.vo.business.base.BaseProductResponse;
import com.eghm.vo.business.item.ActivityItemResponse;
import com.eghm.vo.business.item.ItemDetailVO;
import com.eghm.vo.business.item.ItemResponse;
import com.eghm.vo.business.item.ItemVO;
import com.eghm.vo.business.statistics.ProductStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
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
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 商品列表
     */
    Page<ItemResponse> listPage(Page<ItemResponse> page, @Param("param") ItemQueryRequest request);

    /**
     * 查询店铺首页下推荐的商品列表, 不含下架的商品
     *
     * @param shopId 店铺id
     * @param limit  查询多少条
     * @return 商品列表
     */
    List<ItemVO> getPriorityItem(@Param("shopId") Long shopId, @Param("limit") Integer limit);

    /**
     * 查询店铺下推荐的商品列表, 不含下架的商品
     *
     * @param limit 查询多少条
     * @return 商品列表
     */
    List<ItemVO> getRecommendItem(@Param("limit") Integer limit);

    /**
     * 分页查询商品列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 商品信息
     */
    Page<ItemVO> getByPage(Page<ItemVO> page, @Param("param") ItemQueryDTO dto);

    /**
     * 优惠券所能使用的商品范围, 分页查询
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<ItemVO> getCouponScopeByPage(Page<ItemVO> page, @Param("param") ItemCouponQueryDTO dto);

    /**
     * 根据该订单编号更新该订单下所有商品的销售量
     *
     * @param orderNoList 订单编号
     */
    void updateSaleNum(@Param("orderNoList") List<String> orderNoList);

    /**
     * 查询商品详细信息
     *
     * @param id id
     * @return 商品详细信息
     */
    ItemDetailVO detailById(@Param("id") Long id);

    /**
     * 更新评分
     *
     * @param id    id
     * @param score 评分
     */
    void updateScore(@Param("id") Long id, @Param("score") BigDecimal score);

    /**
     * 查询零售商品信息
     *
     * @param itemIds 商品id
     * @return 商品信息
     */
    List<ItemVO> getList(@Param("itemIds") List<Long> itemIds);

    /**
     * 分页查询商品列表
     *
     * @param page 分页
     * @param request 查询条件
     * @return 基础信息
     */
    Page<BaseProductResponse> getProductPage(Page<BaseProductResponse> page, @Param("param") BaseProductQueryRequest request);

    /**
     * 新增商品列表
     *
     * @param request 查询条件
     * @param tableName 商品表名
     * @return 列表
     */
    List<ProductStatisticsVO> dayAppend(@Param("param") ProductRequest request, @Param("tableName") String tableName);

    /**
     * 查询可以参加活动的商品列表
     *
     * @param merchantId 商户id
     * @param activityId 活动id
     * @return 列表
     */
    List<ActivityItemResponse> getActivityList(@Param("merchantId") Long merchantId, @Param("activityId") Long activityId);

    /**
     * 根据商品id列表查询商品信息
     *
     * @param itemIds 商品ids
     * @return 列表 含删除的商品
     */
    List<Item> getByIds(@Param("ids") List<Long> itemIds);
}
