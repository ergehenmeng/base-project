package com.eghm.service.business;

import com.eghm.service.pay.enums.TradeType;
import com.eghm.service.pay.vo.PrepayVO;

/**
 * 订单相关公共接口
 * @author 二哥很猛
 * @date 2022/7/28
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
     * 订单过期处理(超时未支付)
     * 1.增库存
     * 2.释放优惠券(如果有的话)
     * 3.更新订单状态
     * @param orderNo 订单编号
     */
    void orderExpire(String orderNo);

    /**
     * 订单关闭
     * 1.增库存
     * 2.释放优惠券(如果有的话)
     * 3.更新订单状态
     * @param orderId 订单id
     */
    void orderClose(Long orderId);

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
}
