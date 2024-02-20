package com.eghm.service.business;

import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.dto.business.account.score.ScoreBalanceRechargeDTO;
import com.eghm.dto.business.account.score.ScoreWithdrawApplyDTO;
import com.eghm.model.ScoreAccount;

/**
 * <p>
 * 商户积分账户表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
public interface ScoreAccountService {

    /**
     * 更新商户积分账户
     * 注意: 该方法只有积分真是变动时才会记录变动记录和余额信息, 支付和退款不做记录(订单完成才会触发)
     * @param dto 积分信息
     */
    void updateAccount(ScoreAccountDTO dto);

    /**
     * 查询商户积分信息
     *
     * @param merchantId 商户id
     * @return 积分信息
     */
    ScoreAccount getAccount(Long merchantId);

    /**
     * 订单完成 解冻积分
     *
     * @param merchantId 商户id
     * @param amount 解冻金额
     */
    void orderComplete(Long merchantId, Integer amount);

    /**
     * 提现积分
     *
     * @param dto 查询条件
     */
    void applyWithdraw(ScoreWithdrawApplyDTO dto);

    /**
     * 提现成功 (一般是提现回调中调用该方法)
     *
     * @param tradeNo 交易单号
     */
    void withdrawSuccess(String tradeNo);

    /**
     * 提现失败,解冻积分 (一般是提现回调中调用该方法)
     *
     * @param tradeNo 交易单号
     */
    void withdrawFail(String tradeNo);

    /**
     * 余额方式充值积分
     *
     * @param dto 充值
     */
    void balanceRecharge(ScoreBalanceRechargeDTO dto);

    /**
     * 积分充值成功 (一般在支付成功回调中调用该方法)
     *
     * @param tradeNo 交易单号
     */
    void rechargeSuccess(String tradeNo);
}
