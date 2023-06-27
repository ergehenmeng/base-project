package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.order.ticket.TicketOfflineRefundRequest;
import com.eghm.enums.ref.OrderState;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.service.pay.enums.TradeState;
import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PrepayVO;
import com.eghm.vo.business.order.OrderScanVO;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author 二哥很猛
 * @date 2022/8/17
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建预支付订单
     * @param orderNo 订单编号
     * @param buyerId  付款人id
     * @param tradeType 支付方式
     * @return 拉起支付的信息
     */
    PrepayVO createPrepay(String orderNo, String buyerId, TradeType tradeType);

    /**
     * 根据交易流水号查询订单, 门票,餐饮,线路,民宿
     * @param outTradeNo 交易流水号
     * @return 订单信息
     */
    Order selectByOutTradeNo(String outTradeNo);

    /**
     * 根据交易流水号查询订单 针对普通商品订单可能会出现一个交易流水号对应多类商品订单
     * @param outTradeNo 交易流水号
     * @return 订单列表
     */
    List<Order> selectByOutTradeNoList(String outTradeNo);

    /**
     * 查询订单信息
     * @param orderId 主键
     * @return 订单信息
     */
    Order selectById(Long orderId);

    /**
     * 根据订单查询订单信息,
     * 如果订单已删除或者未支付则抛异常
     * @param orderNoList 订单编号
     * @return 订单信息
     */
    List<Order> getUnPay(List<String> orderNoList);

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
     * 更新订单为处理中, 该接口为补充接口
     * 如果用户支付完直接杀进程,可能不会将订单改为支付处理中
     * 注意: 只有带支付的订单才会修改为支付处理中
     * @param orderId 订单号
     * @param memberId 用户id
     */
    void setProcess(Long orderId, Long memberId);

    /**
     * 订单删除
     * 已取消或者已关闭的订单才能删除
     * @param orderId 订单id
     * @param memberId  用户id
     */
    void deleteOrder(Long orderId, Long memberId);

    /**
     * 查询支付处理中的订单列表
     * @return 订单信息
     */
    List<Order> getProcessList();

    /**
     * 发起退款操作
     * @param log 退款记录
     * @param order 订单
     */
    void startRefund(OrderRefundLog log, Order order);

    /**
     * 更新订单状态, 注意更新订单状态时,订单当前状态必须在旧状态中
     * @param orderNoList 订单列表
     * @param newState 新状态
     * @param oldState 旧状态
     */
    void updateState(List<String> orderNoList, OrderState newState, OrderState... oldState);

    /**
     * 更新订单状态, 注意更新订单状态时,订单当前状态必须在旧状态中- 重载方法
     * @param orderNo  订单号
     * @param newState 新状态
     * @param oldState 旧状态
     */
    void updateState(String orderNo, OrderState newState, OrderState... oldState);

    /**
     * 核销码解码
     * @param verifyNo 核销码
     * @return 订单号
     */
    String decryptVerifyNo(String verifyNo);

    /**
     * 加密订单号
     * @param orderNo 订单号
     * @return 核销码
     */
    String encryptVerifyNo(String orderNo);

    /**
     * 门票下线退款
     * @param request 退款金额
     */
    void ticketOfflineRefund(TicketOfflineRefundRequest request);

    /**
     * 查询扫码后的订单结果
     * @param orderNo 订单编号
     * @return 订单及游客信息
     */
    OrderScanVO getScanResult(String orderNo);

    /**
     * 订单状态变更处理, 注意:只有订单状态变动时才需要调用该方法
     * @param order 订单信息
     */
    void orderStateModify(Order order);

    /**
     * 订单状态变更时所做的字段变更, 注意:只有订单状态变动时才需要调用该方法
     * @param order 订单信息
     * @param closeConsumer 关闭订单的特殊处理
     */
    void orderStateModify(Order order, Consumer<Order> closeConsumer);
}
