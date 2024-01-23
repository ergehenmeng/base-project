package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.model.Order;
import com.eghm.vo.business.statistics.OrderStatisticsVO;
import com.eghm.vo.business.order.ProductSnapshotVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-17
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list
     */
    List<ProductSnapshotVO> getItemList(@Param("orderNo") String orderNo);

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list 一般只有一个
     */
    List<ProductSnapshotVO> getRestaurantList(@Param("orderNo") String orderNo);

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list 一般只有一个
     */
    List<ProductSnapshotVO> getTicketList(@Param("orderNo") String orderNo);

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list 一般只有一个
     */
    List<ProductSnapshotVO> getLineList(@Param("orderNo") String orderNo);

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list 一般只有一个
     */
    List<ProductSnapshotVO> getHomestayList(@Param("orderNo") String orderNo);

    /**
     * 获取订单统计
     *
     * @param request 查询条件
     * @return 订单统计
     */
    List<OrderStatisticsVO> dayOrder(DateRequest request);

    /**
     * 获取订单统计
     *
     * @param request 查询条件
     * @return 订单统计
     */
    OrderStatisticsVO orderStatistics(DateRequest request);
}
