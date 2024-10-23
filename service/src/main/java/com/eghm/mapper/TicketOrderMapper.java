package com.eghm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.model.TicketOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.ticket.TicketOrderDetailResponse;
import com.eghm.vo.business.order.ticket.TicketOrderDetailVO;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import com.eghm.vo.business.order.ticket.TicketOrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 门票订单表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-07-12
 */
public interface TicketOrderMapper extends BaseMapper<TicketOrder> {

    /**
     * 分页查询门票订单
     *
     * @param page    分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<TicketOrderResponse> getByPage(Page<TicketOrderResponse> page, @Param("param") TicketOrderQueryRequest request);

    /**
     * 查询用户门票订单列表
     *
     * @param page 分页信息
     * @param dto  查询条件
     * @return 列表
     */
    Page<TicketOrderVO> getList(Page<TicketOrderVO> page, @Param("param") TicketOrderQueryDTO dto);

    /**
     * 查询用户自己的订单详情信息
     *
     * @param orderNo  订单编号
     * @param memberId 用户id
     * @return 订单信息
     */
    TicketOrderDetailVO getDetail(@Param("orderNo") String orderNo, @Param("memberId") Long memberId);

    /**
     * 查询订单商品快照
     *
     * @param orderId 订单id
     * @return 订单信息
     */
    ProductSnapshotVO getSnapshot(@Param("orderId") Long orderId, @Param("orderNo") String orderNo);

    /**
     * 查询门票订单详细信息
     *
     * @param orderNo    订单编号
     * @param merchantId 商户ID
     * @return 订单详情
     */
    TicketOrderDetailResponse detail(@Param("orderNo") String orderNo, @Param("merchantId") Long merchantId);
}
