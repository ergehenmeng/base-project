package com.eghm.service.business;

import com.eghm.dao.model.TicketOrder;
import com.eghm.model.dto.business.order.ticket.ApplyRefundDTO;
import com.eghm.model.dto.business.order.ticket.CreateTicketOrderDTO;

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
     * 门票退款申请
     * 1. 校验订单状态是否符合退款要求
     * 2. 增加退款申请记录
     * 3. 如果存在实名制用户,则锁定用户防止用户在退款中继续核销
     * @param dto 退款信息
     */
    void applyRefund(ApplyRefundDTO dto);

    /**
     * 订单过期处理(超时未支付)
     * 1.增库存
     * 2.释放优惠券(如果有的话)
     * 3.更新订单状态
     * @param orderNo 订单编号
     */
    void orderExpire(String orderNo);

}
