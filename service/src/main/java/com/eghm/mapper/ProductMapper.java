package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.model.Product;
import com.eghm.model.dto.business.product.ProductQueryDTO;
import com.eghm.model.dto.business.product.ProductQueryRequest;
import com.eghm.model.vo.business.product.ProductListResponse;
import com.eghm.model.vo.business.product.ProductListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-01
 */
public interface ProductMapper extends BaseMapper<Product> {

    /**
     * 更新销售量
     * @param productId 商品ID
     * @param num 销售数量
     */
    void updateSaleNum(@Param("productId") Long productId, @Param("num") Integer num);

    /**
     * 根据该订单编号更新该订单下所有商品的销售量
     * @param orderNo 订单编号
     */
    void updateSaleNumByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 分页查询商品信息
     * @param page 分页信息
     * @param request 查询条件
     * @return 商品列表
     */
    Page<ProductListResponse> listPage(Page<ProductListResponse> page, @Param("param")ProductQueryRequest request);

    /**
     * 查询店铺下推荐的商品列表, 不含下架的商品
     * @param shopId 店铺id
     * @param limit 查询多少条
     * @return 商品列表
     */
    List<ProductListVO> getRecommendProduct(@Param("shopId") Long shopId, @Param("shopId") Integer limit);

    /**
     * 分页查询商品列表
     * @param page  分页信息
     * @param dto 查询条件
     * @return 商品信息
     */
    Page<ProductListVO> getByPage(Page<ProductListVO> page, @Param("param") ProductQueryDTO dto);
}
