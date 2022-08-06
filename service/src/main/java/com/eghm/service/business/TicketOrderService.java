package com.eghm.service.business;

import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.ApplyTicketRefundDTO;
import com.eghm.model.dto.business.order.ticket.AuditTicketRefundRequest;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;
import com.eghm.service.pay.enums.TradeState;

/**
 * @author 二哥很猛
 * @date 2022/7/12
 */
public interface TicketOrderService {

    /**
     * 创建门票订单
     * 1. 校验门票信息
     * 2. 校验优惠券信息(如果有的话)
     * 3. 创建订单
     * 4. 增加联系人(如果有的话)
     * 5. 更新库存
     * 6. MQ门票订单超时处理
     * @param dto 下单信息
     */
    void create(CreateTicketOrderDTO dto);

    /**
     * 根据订单编号查询订单订单信息
     * @param orderNo 订单编号
     * @return 门票订单信息
     */
    TicketOrder selectByOrderNo(String orderNo);

    /**
     * 根据交易流水号查询门票订单
     * @param outTradeNo 交易流水号
     * @return 订单信息
     */
    TicketOrder selectByOutTradeNo(String outTradeNo);

    /**
     * 根据主键查询订单信息,
     * 如果订单已删除或者未支付则抛异常
     * @param id id
     * @return 订单信息
     */
    TicketOrder getUnPayById(Long id);

    /**
     * 门票订单是否已支付
     * @param order 订单信息
     * @return true: 支付处理或已支付 false: 未支付
     */
    TradeState getOrderPayState(TicketOrder order);

    /**
     * 门票退款申请
     * 1. 校验订单状态是否符合退款要求
     * 2. 增加退款申请记录
     * 3. 如果存在实名制用户,则锁定用户防止用户在退款中继续核销
     * @param dto 退款信息
     */
    void applyRefund(ApplyTicketRefundDTO dto);

    /**
     * 退款审核
     * 1. 审核状态校验
     * 2. 退款拒接,则解锁锁定的用户信息
     * 3. 退款通过,则发起退款申请
     * 4. 更新订单为退款中
     * 5. 更新退款记录为退款中
     * 6. 根据异步回调信息进行最终状态的更新
     * @param request 审核信息
     */
    void auditRefund(AuditTicketRefundRequest request);
}
