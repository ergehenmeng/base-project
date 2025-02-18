package com.eghm.service.business;

import com.eghm.dto.business.account.AccountDTO;
import com.eghm.model.Account;
import com.eghm.model.Order;

/**
 * <p>
 * 商户账户信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
public interface AccountService {

    /**
     * 新增资金变动记录
     * 注意: 该方法只有在资金真实变动时才会更新账户余额和资金记录
     * @param dto 变动信息
     */
    void updateAccount(AccountDTO dto);

    /**
     * 订单完成 解冻支付金额
     *
     * @param merchantId 商户id
     * @param orderNo 订单号
     */
    void orderComplete(Long merchantId, String orderNo);

    /**
     * 查询商户账户信息
     *
     * @param merchantId 商户id
     * @return 账户信息
     */
    Account getAccount(Long merchantId);

    /**
     * 支付成功后,增加冻结记录
     *
     * @param order 订单信息
     */
    void paySuccessAddFreeze(Order order);

    /**
     * 退款成功后,更新冻结记录
     *
     * @param order 订单信息
     * @param refundAmount 本次退款金额
     * @param refundNo  退款流水号
     */
    void refundSuccessUpdateFreeze(Order order, Integer refundAmount, String refundNo);
}
