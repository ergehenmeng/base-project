package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.dto.ext.OrderRefund;
import com.eghm.model.OrderRefundLog;
import com.eghm.vo.business.order.refund.RefundLogResponse;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/1
 */
public interface OrderRefundLogService {

    /**
     * 退款记录列表
     *
     * @param request 查询条件
     * @return 记录
     */
    Page<RefundLogResponse> getByPage(RefundLogQueryRequest request);

    /**
     * 新增退款记录
     *
     * @param log 退款记录
     */
    void insert(OrderRefundLog log);

    /**
     * 根据id查询退款记录
     *
     * @param id id
     * @return 退款记录
     */
    OrderRefundLog selectById(Long id);

    /**
     * 主键查询退款记录
     *
     * @param id id
     * @return 退款记录
     */
    OrderRefundLog selectByIdRequired(Long id);

    /**
     * 根据订单号查询退款记录
     *
     * @param orderNo 订单号
     * @param visitorId 退款人信息
     * @return 退款记录
     */
    OrderRefundLog getVisitRefundLog(String orderNo, Long visitorId);

    /**
     * 根据id更新退款记录
     *
     * @param log 退款记录
     * @return 1条
     */
    int updateById(OrderRefundLog log);

    /**
     * 统计订单总退款商品数量 包含退款处理中的
     *
     * @param orderNo     订单编号
     * @param itemOrderId 商品订单id, 普通商品时该字段必填
     * @return 数量
     */
    int getTotalRefundNum(String orderNo, Long itemOrderId);

    /**
     * 统计退款成功的订单商品总数量
     *
     * @param orderNo     订单编号
     * @param itemOrderId 商品订单id, 普通商品时该字段必填
     * @return 数量
     */
    int getRefundSuccessNum(String orderNo, Long itemOrderId);

    /**
     * 根据退款流水或查询退款记录
     *
     * @param refundNo 退款流水或
     * @return 退款信息 可能为空
     */
    OrderRefundLog selectByRefundNo(String refundNo);

    /**
     * 查询所有退款处理中的订单信息
     *
     * @return 退款信息
     */
    List<OrderRefund> getRefundProcess();

    /**
     * 检查订单是否存在已退款或者审核通过在退款中的订单
     * 如果退款记录审核通过, 在退款中或退款成功,则不允许再次退款
     *
     * @param orderNo     订单编号
     * @param visitorList 待退款游客id
     * @return true:存在 false:不存在
     */
    boolean hasRefundSuccess(String orderNo, List<Long> visitorList);

    /**
     * 查询已退款或者退款中的退款记录
     *
     * @param orderNo 订单编号
     * @return 退款记录列表
     */
    List<OrderRefundLog> getRefundLog(String orderNo);
}
