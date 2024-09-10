package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.model.Order;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.statistics.OrderCardVO;
import com.eghm.vo.business.statistics.OrderStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
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
    OrderCardVO orderStatistics(DateRequest request);

    /**
     * 退款订单统计
     *
     * @param request 查询条件
     * @return 订单统计
     */
    OrderCardVO orderRefundStatistics(DateRequest request);

    /**
     * 更新拼团订单状态
     *
     * @param bookingNo 拼团id
     * @param orderNo 订单编号
     * @param bookingState 状态
     */
    void updateBookingState(@Param("bookingNo") String bookingNo, @Param("orderNo") String orderNo, @Param("bookingState") Integer bookingState);

    /**
     * 查询指定日期范围内订单的会员id
     *
     * @param orderTime 订单时间
     * @return 列表
     */
    List<Long> getOrderMember(@Param("orderTime") LocalDate orderTime);

    /**
     * 查询指定日期范围内符合指定消费次数的订单会员id
     *
     * @param orderTime 订单时间
     * @param consumeNum 消费次数
     * @return 列表
     */
    List<Long> getOrderNum(@Param("orderTime") LocalDate orderTime, @Param("consumeNum") Integer consumeNum);

    /**
     * 查询指定日期范围内符合指定消费次数的订单会员id
     *
     * @param orderTime 订单时间
     * @param consumeAmount 最低消费金额
     * @return 列表
     */
    List<Long> getOrderAmount(@Param("orderTime") LocalDate orderTime, @Param("consumeAmount") Integer consumeAmount);

    /**
     * 待发货零售商品数量
     *
     * @param merchantId 商户ID
     * @return 待发货零售商品数量
     */
    Integer waitDelivery(@Param("merchantId") Long merchantId);

    /**
     * 待退款审核后的零售商品数量
     *
     * @param merchantId 商户ID
     * @return 待退款审核后的零售商品数量
     */
    Integer refunding(@Param("merchantId") Long merchantId);

    /**
     * 待核销的数量
     *
     * @param merchantId 商户ID
     * @return 待核销的数量
     */
    Integer waitVerify(@Param("merchantId") Long merchantId);
}
