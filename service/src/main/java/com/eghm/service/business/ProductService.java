package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ref.PlatformState;
import com.eghm.common.enums.ref.State;
import com.eghm.model.Product;
import com.eghm.model.dto.business.product.*;
import com.eghm.model.vo.business.product.ProductListResponse;
import com.eghm.model.vo.business.product.ProductListVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 二哥很猛
 * @date 2022/7/1
 */
public interface ProductService {

    /**
     * 分页查询商品信息
     * @param request 分页信息及查询条件
     * @return 商品列表
     */
    Page<ProductListResponse> getByPage(ProductQueryRequest request);

    /**
     * 增加商品信息
     * @param request 商品信息
     */
    void create(ProductAddRequest request);

    /**
     * 更新商品信息
     * @param request 特产商品
     */
    void update(ProductEditRequest request);

    /**
     * 主键查询商品信息
     * @param id id
     * @return 商品信息
     */
    Product selectById(Long id);

    /**
     * 主键查询商品信息
     * @param id id
     * @return 商品信息
     */
    Product selectByIdRequired(Long id);

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

    /**
     * 设置商品为推荐商品
     * @param id 商品id
     */
    void setRecommend(Long id);

    /**
     * 商品排序
     * @param id 商品id
     * @param sortBy 排序 最大999
     */
    void sortBy(Long id, Integer sortBy);

    /**
     * 根据id查询商品
     * @param ids 商品id
     * @return 商品信息 key为商品id
     */
    Map<Long, Product> getByIds(Set<Long> ids);

    /**
     * 更新销售量
     * @param productNumMap 商品及销售数量
     */
    void updateSaleNum(Map<Long, Integer> productNumMap);

    /**
     * 更新销售量
     * @param id  商品id
     * @param num 销量量-减销量 +增销量
     */
    void updateSaleNum(Long id, Integer num);

    /**
     * 根据订单编号更新订单号下的所有商品销售量
     * @param orderNoList 订单号
     */
    void updateSaleNum(List<String> orderNoList);

    /**
     * 查询<b>店铺首页</b>推荐的商品, 注意:
     * 1. 如果推荐商品本身没有规格,则不会优先展示
     * 2. 如果没有推荐默认以最新且上架的商品进行展示
     * @param shopId 店铺id
     * @return 推荐商品
     */
    List<ProductListVO> getPriorityProduct(Long shopId);

    /**
     * 查询<b>首页</b>推荐的商品, 注意:
     * 1. 如果推荐商品本身没有规格,则不会优先展示
     * 2. 如果没有推荐默认以最新且上架的商品进行展示
     * @return 推荐商品
     */
    List<ProductListVO> getRecommend();

    /**
     * 商品列表查询 移动端
     * @param dto 查询条件
     * @return 列表
     */
    List<ProductListVO> getByPage(ProductQueryDTO dto);

    /**
     * 优惠券所能使用的商品范围, 分页查询
     * @param dto 查询条件
     * @return 列表
     */
    List<ProductListVO> getCouponScopeByPage(ProductCouponQueryDTO dto);
}
