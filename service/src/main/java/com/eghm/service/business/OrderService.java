package com.eghm.service.business;

import com.eghm.common.enums.ref.OrderState;
import com.eghm.dao.model.Order;
import com.eghm.dao.model.OrderRefundLog;
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
    PrepayVO createPrepay(Long orderId, String buyerId, TradeType tradeType);

    /**
     * 添加主订单信息
     * @param order 订单信息
     */
    void insert(Order order);

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
     * 更新订单为处理中, 该接口为补充接口
     * 如果用户支付完直接杀进程,可能不会将订单改为支付处理中
     * 注意: 只有带支付的订单才会修改为支付处理中
     * @param orderId 订单号
     * @param userId 用户id
     */
    void setProcess(Long orderId, Long userId);

    /**
     * 订单删除
     * 已取消或者已关闭的订单才能删除
     * @param orderId 订单id
     * @param userId  用户id
     */
    void deleteOrder(Long orderId, Long userId);

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
}
