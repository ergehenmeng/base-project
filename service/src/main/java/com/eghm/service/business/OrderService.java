package com.eghm.service.business;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eghm.dto.business.order.OfflineRefundRequest;
import com.eghm.dto.business.order.item.ItemSippingRequest;
import com.eghm.dto.statistics.DateRequest;
import com.eghm.enums.ref.OrderState;
import com.eghm.enums.ref.PayType;
import com.eghm.enums.ref.ProductType;
import com.eghm.model.Order;
import com.eghm.model.OrderRefundLog;
import com.eghm.pay.enums.TradeState;
import com.eghm.pay.enums.TradeType;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.vo.business.order.OrderScanVO;
import com.eghm.vo.business.order.ProductSnapshotVO;
import com.eghm.vo.business.order.item.ExpressDetailVO;
import com.eghm.vo.business.order.item.ItemOrderRefundVO;
import com.eghm.vo.business.statistics.OrderCardVO;
import com.eghm.vo.business.statistics.OrderStatisticsVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/8/17
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建预支付订单
     *
     * @param orderNo   订单编号
     * @param buyerId   付款人id
     * @param tradeType 支付方式
     * @param clientIp  ip
     * @return 拉起支付的信息
     */
    PrepayVO createPrepay(String orderNo, String buyerId, TradeType tradeType, String clientIp);

    /**
     * 根据交易流水号查询订单, 门票,餐饮,线路,民宿
     *
     * @param tradeNo 交易流水号
     * @return 订单信息
     */
    Order selectByTradeNo(String tradeNo);

    /**
     * 根据交易流水号查询订单 针对普通商品订单可能会出现一个交易流水号对应多类商品订单
     *
     * @param tradeNo 交易流水号
     * @return 订单列表
     */
    List<Order> selectByTradeNoList(String tradeNo);

    /**
     * 查询订单信息
     *
     * @param orderId 主键
     * @return 订单信息
     */
    Order selectById(Long orderId);

    /**
     * 根据订单查询订单信息,
     * 如果订单已删除或者未支付则抛异常
     *
     * @param orderNoList 订单编号
     * @return 订单信息
     */
    List<Order> getUnPay(List<String> orderNoList);

    /**
     * 订单是否已支付
     *
     * @param order 订单信息
     * @return true: 支付处理或已支付 false: 未支付
     */
    TradeState getOrderPayState(Order order);

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getByOrderNo(String orderNo);

    /**
     * 订单删除
     * 已取消或者已关闭的订单才能删除
     *
     * @param orderNo  订单号
     * @param memberId 用户id
     */
    void deleteOrder(String orderNo, Long memberId);

    /**
     * 查询支付处理中的订单列表
     *
     * @return 订单信息
     */
    List<Order> getProcessList();

    /**
     * 发起退款操作
     *
     * @param log   退款记录
     * @param order 订单
     */
    void startRefund(OrderRefundLog log, Order order);

    /**
     * 更新订单状态, 注意更新订单状态时,订单当前状态必须在旧状态中
     *
     * @param orderNoList 订单列表
     * @param payTime  只有支付成功的状态此字段才不为空
     * @param newState    新状态
     * @param oldState    旧状态
     */
    void updateState(List<String> orderNoList, LocalDateTime payTime, OrderState newState, Object... oldState);

    /**
     * 更新订单状态, 注意更新订单状态时,订单当前状态必须在旧状态中- 重载方法
     *
     * @param orderNo  订单号
     * @param newState 新状态
     * @param oldState 旧状态
     */
    void updateState(String orderNo, OrderState newState, Object... oldState);

    /**
     * 支付成功, 更新订单状态,生成核销码
     *
     * @param orderNo  订单编号
     * @param verifyNo 核销码
     * @param payTime  支付成功时间
     * @param newState 成功状态
     * @param payType  支付方式
     */
    void paySuccess(String orderNo, String verifyNo, LocalDateTime payTime, OrderState newState, PayType payType);

    /**
     * 核销码解码
     *
     * @param verifyNo 核销码
     * @return 订单号
     */
    String decryptVerifyNo(String verifyNo);

    /**
     * 加密订单号
     *
     * @param verifyNo 核销码
     * @return 核销码
     */
    String encryptVerifyNo(String verifyNo);

    /**
     * 下线退款
     *
     * @param request 退款金额
     */
    void offlineRefund(OfflineRefundRequest request);

    /**
     * 查询扫码后的订单结果
     *
     * @param verifyNo   核销码
     * @param merchantId 商户ID
     * @return 订单及游客信息
     */
    OrderScanVO getScanResult(String verifyNo, Long merchantId);

    /**
     * 订单状态变更处理, 注意:只有订单状态变动时才需要调用该方法
     *
     * @param order 订单信息
     */
    void orderStateModify(Order order);

    /**
     * 根据订单号查询订单下的商品信息
     *
     * @param orderNo 订单号
     * @return list
     */
    List<ProductSnapshotVO> getProductList(String orderNo);

    /**
     * 刷新核销码
     *
     * @param orderNo  订单编号
     * @param memberId 用户ID
     * @return 核销码
     */
    String refreshVerifyCode(String orderNo, Long memberId);

    /**
     * 根据核销码查询订单
     *
     * @param verifyNo 核销码
     * @return 订单信息
     */
    Order getByVerifyNo(String verifyNo);

    /**
     * 零售发货
     * 1. 校验商品订单必须是待发货的
     * 2. 更新商品订单状态
     * 3. 增加订单与商品关联表
     * 4. 更新主订单状态
     *
     * @param request 发货信息
     */
    void sipping(ItemSippingRequest request);

    /**
     * 查询订单物流信息
     *
     * @param id id
     * @return vo
     */
    ExpressDetailVO expressDetail(Long id);

    /**
     * 确认收货(零售)
     *
     * @param orderNo 订单号
     * @param memberId 用户id
     */
    void confirmReceipt(String orderNo, Long memberId);

    /**
     * 订单分账
     *
     * @param orderNo 订单号
     */
    void routing(String orderNo);

    /**
     * 累计订单统计
     *
     * @param request request
     * @return list
     */
    OrderCardVO orderStatistics(DateRequest request);

    /**
     * 订单统计按天(按月)
     *
     * @param request request
     * @return list
     */
    List<OrderStatisticsVO> dayOrder(DateRequest request);

    /**
     * 更新拼团状态
     *
     * @param bookingNo 拼团单号
     * @param bookingState 拼团状态
     */
    void updateBookingState(String bookingNo, Integer bookingState);

    /**
     * 检查当前用户是否已在拼团订单中
     * 一个用户在一个拼团中只能下单一次
     * @param bookingNo 拼团单号
     * @param memberId 用户id
     */
    void checkGroupOrder(String bookingNo, Long memberId);

    /**
     * 待退款信息
     *
     * @param orderId  商品订单id
     * @param memberId 用户ID
     * @param containAddress 是否包含仓库地址
     * @return 退款信息
     */
    ItemOrderRefundVO getItemRefund(Long orderId, Long memberId, boolean containAddress);

    /**
     * 按商户订单商品交易金额
     *
     * @param productType 商品类型
     * @param merchantId  商户id
     * @param productId 商品id
     * @param amount 交易金额
     */
    void incrementAmount(ProductType productType, Long merchantId, Long productId, Integer amount);

}
