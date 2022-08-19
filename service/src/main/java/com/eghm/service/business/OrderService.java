package com.eghm.service.business;

import com.eghm.common.enums.ref.CloseType;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
import com.eghm.model.dto.business.order.ticket.AuditTicketRefundRequest;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PrepayVO;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface OrderService {

    /**
     * 创建预支付订单
     * @param orderId 订单id
     * @param buyerId  付款人id
     * @param tradeType 支付方式
     * @return 拉起支付的信息
     */
    PrepayVO prepay(Long orderId, String buyerId, TradeType tradeType);

    /**
     * 添加主订单信息
     * @param order 订单信息
     */
    void insert(Order order);

    /**
     * 根据交易流水号查询订单
     * @param outTradeNo 交易流水号
     * @return 订单信息
     */
    Order selectByOutTradeNo(String outTradeNo);

    /**
     * 查询订单信息
     * @param orderId 主键
     * @return 订单信息
     */
    Order selectById(Long orderId);

    /**
     * 更新订单信息
     * @param order 订单信息
     */
    void updateById(Order order);

    /**
     * 根据主键查询订单信息,
     * 如果订单已删除或者未支付则抛异常
     * @param id id
     * @return 订单信息
     */
    Order getUnPayById(Long id);

    /**
     * 订单是否已支付
     * @param order 订单信息
     * @return true: 支付处理或已支付 false: 未支付
     */
    TradeState getOrderPayState(Order order);

    /**
     * 根据订单号查询订单信息
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getByOrderNo(String orderNo);

    /**
     * 订单关闭
     * @param orderNo 订单编号
     * @param closeType 关闭方式
     * @return 订单信息
     */
    Order closeOrder(String orderNo, CloseType closeType);

    /**
     * 更新订单为处理中, 该接口为补充接口
     * 如果用户支付完直接杀进程,可能不会将订单改为支付处理中
     * 注意: 只有带支付的订单才会修改为支付处理中
     * @param orderId 订单号
     * @param userId 用户id
     */
    void orderPaying(Long orderId, Long userId);

    /**
     * 订单删除
     * 已取消或者已关闭的订单才能删除
     * @param orderId 订单id
     * @param userId  用户id
     */
    void orderDelete(Long orderId, Long userId);

    /**
     * 订单成功或失败的异步处理
     * 根据第三方返回的订单状态来更新本地订单支付状态
     * @param orderNo 订单编号
     */
    void orderPayNotify(String orderNo);

    /**
     * 退款成功货失败的异步处理
     * 1. 查询第三方退款状态
     * 2. 更新本地订单及退款记录
     * 3. 假如退款失败,则由定时任务继续执行退款操作,但是对退款用户来说,他看到一直是退款中
     * @param outTradeNo 支付交易流水号
     * @param outRefundNo 退款交易流水号
     * @return 退款是否成功
     */
    boolean orderRefundNotify(String outTradeNo, String outRefundNo);

    /**
     * 查询支付处理中的订单列表
     * @return 订单号
     */
    List<String> getPayingList();

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

    /**
     * 发起退款操作
     * @param log 退款记录
     * @param order 订单
     */
    void startRefund(OrderRefundLog log, Order order);

    /**
     * 取消订单
     * 1.增库存
     * 2.释放优惠券(如果有的话)
     * 3.更新订单状态
     * @param orderId 订单id
     */
    void orderCancel(Long orderId);

}
