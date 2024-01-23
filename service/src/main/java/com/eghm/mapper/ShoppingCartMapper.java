package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.shopping.ShoppingCartQueryRequest;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.model.ShoppingCart;
import com.eghm.vo.business.shopping.ShoppingCartItemVO;
import com.eghm.vo.business.shopping.ShoppingCartResponse;
import com.eghm.vo.business.statistics.CartStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 购物车表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-23
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

    /**
     * 分页统计购物车商品
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<ShoppingCartResponse> getByPage(Page<ShoppingCartResponse> page, @Param("param") ShoppingCartQueryRequest request);

    /**
     * 查询用户购物车商品列表
     *
     * @param memberId 用户id
     * @return 商品列表
     */
    List<ShoppingCartItemVO> getList(@Param("memberId") Long memberId);

    /**
     * 购物车统计
     *
     * @param request 统计条件
     * @return 列表
     */
    List<CartStatisticsVO> dayCart(DateRequest request);
}
