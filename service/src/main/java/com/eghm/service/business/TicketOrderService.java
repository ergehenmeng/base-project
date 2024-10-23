package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.ticket.TicketOrderQueryDTO;
import com.eghm.dto.business.order.ticket.TicketOrderQueryRequest;
import com.eghm.model.TicketOrder;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.ticket.TicketOrderDetailResponse;
import com.eghm.vo.business.order.ticket.TicketOrderDetailVO;
import com.eghm.vo.business.order.ticket.TicketOrderResponse;
import com.eghm.vo.business.order.ticket.TicketOrderVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 分页查询门票订单列表
     *
     * @param request 查询条件
     * @return 订单列表
     */
    Page<TicketOrderResponse> getByPage(TicketOrderQueryRequest request);

    /**
     * 分页查询门票订单列表 导出使用
     *
     * @param request 查询条件
     * @return 订单列表
     */
    List<TicketOrderResponse> getList(TicketOrderQueryRequest request);

    /**
     * 插入门票订单信息
     *
     * @param order 门票订单
     */
    void insert(TicketOrder order);

    /**
     * 根据订单编号查询订单订单信息
     *
     * @param orderNo 订单编号
     * @return 门票订单信息
     */
    TicketOrder getByOrderNo(String orderNo);

    /**
     * 分页查询用户订单列表
     *
     * @param dto 查询条件
     * @return 列表
     */
    List<TicketOrderVO> getByPage(TicketOrderQueryDTO dto);

    /**
     * 订单详情
     *
     * @param orderNo  订单号
     * @param memberId 用户id
     * @return 详情
     */
    TicketOrderDetailVO getDetail(String orderNo, Long memberId);

    /**
     * 查询商品订单,如果为空则抛异常
     *
     * @param id id
     * @return 门票订单信息
     */
    TicketOrder selectByIdRequired(Long id);

    /**
     * 查询订单商品快照
     *
     * @param orderId 订单id
     * @param orderNo 订单编号
     * @return 商品基础信息
     */
    ProductSnapshotVO getSnapshot(Long orderId, String orderNo);

    /**
     * 查询门票订单详情
     *
     * @param orderNo 订单编号
     * @return 订单详细信息
     */
    TicketOrderDetailResponse detail(String orderNo);
}
