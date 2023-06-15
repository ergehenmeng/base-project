package com.eghm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.order.refund.RefundLogQueryRequest;
import com.eghm.model.OrderRefundLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dto.ext.OrderRefund;
import com.eghm.vo.business.order.refund.RefundLogResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单退款记录表 Mapper 接口
 * </p>
 *
 * @author 二哥很猛
 * @since 2022-08-01
 */
public interface OrderRefundLogMapper extends BaseMapper<OrderRefundLog> {

    /**
     * 统计订单总退款数量 包含退款成功和退款中
     * 1.普通商品会存在一个订单号对应多个商品订单, 而每个商品订单数量会大于1
     * 2.特殊商品一个订单号只会对应一个商品订单
     * 因此,在统计普通商品退款数量时,需要定位某个商品退款的数量
     * @param orderNo 订单编号
     * @param itemOrderId 商品订单id
     * @return 数量
     */
    int getTotalRefundNum(@Param("orderNo") String orderNo, @Param("itemOrderId") Long itemOrderId);

    /**
     * 统计退款成功的商品数量
     * @param orderNo 订单编号
     * @param itemOrderId 商品订单id
     * @return 数量
     */
    int getRefundSuccessNum(@Param("orderNo") String orderNo, @Param("itemOrderId") Long itemOrderId);

    /**
     * 查询款处理中的订单流水号及退款流水号
     * @return 流水号信息
     */
    List<OrderRefund> getRefundProcess();

    /**
     * 分页查询退款记录列表
     * @param page 分页信息
     * @param request 查询条件
     * @return 列表
     */
    Page<RefundLogResponse> getByPage(Page<RefundLogResponse> page, @Param("param") RefundLogQueryRequest request);

    /**
     * 查询退款成功审核记录列表
     * @param orderNo 订单编号
     * @param visitorList 游客列表
     * @return 数量
     */
    int getRefundSuccess(@Param("orderNo") String orderNo, @Param("visitorList") List<Long> visitorList);
}
