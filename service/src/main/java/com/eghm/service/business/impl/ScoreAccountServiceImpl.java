package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.dto.business.account.score.ScoreRechargeDTO;
import com.eghm.dto.business.account.score.ScoreScanRechargeDTO;
import com.eghm.dto.business.account.score.ScoreWithdrawApplyDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.AccountType;
import com.eghm.enums.ChargeType;
import com.eghm.enums.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ScanRechargeLogMapper;
import com.eghm.mapper.ScoreAccountLogMapper;
import com.eghm.mapper.ScoreAccountMapper;
import com.eghm.model.*;
import com.eghm.pay.AggregatePayService;
import com.eghm.pay.dto.PrepayDTO;
import com.eghm.pay.enums.PayChannel;
import com.eghm.pay.enums.TradeType;
import com.eghm.pay.vo.PrepayVO;
import com.eghm.service.business.AccountLogService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.DecimalUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.constants.CommonConstant.*;

/**
 * <p>
 * 商户积分账户表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-12
 */
@Slf4j
@AllArgsConstructor
@Service("scoreAccountService")
public class ScoreAccountServiceImpl implements ScoreAccountService, MerchantInitService {

    private final JsonService jsonService;

    private final AlarmService alarmService;

    private final AccountService accountService;

    private final AccountLogService accountLogService;

    private final ScoreAccountMapper scoreAccountMapper;

    private final AggregatePayService aggregatePayService;

    private final ScoreAccountLogMapper scoreAccountLogMapper;

    private final ScanRechargeLogMapper scanRechargeLogMapper;

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return true;
    }

    @Override
    public void init(Merchant merchant) {
        ScoreAccount account = new ScoreAccount();
        account.setMerchantId(merchant.getId());
        scoreAccountMapper.insert(account);
    }

    @Override
    public void updateAccount(ScoreAccountDTO dto) {
        log.info("更新商户积分账户 [{}]", jsonService.toJson(dto));
        ScoreAccount account = this.getAccount(dto.getMerchantId());
        if (dto.getChargeType() == ChargeType.RECHARGE) {
            // 充值回调中调用该方法
            account.setAmount(account.getAmount() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.ORDER_PAY) {
            // 支付回调中调用该方法
            account.setPayFreeze(account.getPayFreeze() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.ORDER_REFUND) {
            // 退款回调中调用该方法
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.DRAW || dto.getChargeType() == ChargeType.ATTENTION_GIFT) {
            account.setAmount(account.getAmount() - dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.WITHDRAW) {
            // 提现申请中调用该方法
            account.setAmount(account.getAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.WITHDRAW_FAIL) {
            // 提现回调中调用该方法
            account.setAmount(account.getAmount() + dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() - dto.getAmount());
        }
        this.updateById(account);
        ScoreAccountLog accountLog = DataUtil.copy(dto, ScoreAccountLog.class);
        accountLog.setDirection(dto.getChargeType().getDirection());
        accountLog.setSurplusAmount(account.getAmount() + account.getPayFreeze());
        scoreAccountLogMapper.insert(accountLog);

    }

    @Override
    public ScoreAccount getAccount(Long merchantId) {
        LambdaQueryWrapper<ScoreAccount> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScoreAccount::getMerchantId, merchantId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return scoreAccountMapper.selectOne(wrapper);
    }

    @Override
    public void orderComplete(Long merchantId, Integer amount) {
        ScoreAccount account = this.getAccount(merchantId);
        account.setAmount(account.getAmount() + amount);
        account.setAmount(account.getPayFreeze() - amount);
        this.updateById(account);
    }

    @Override
    public void applyWithdraw(ScoreWithdrawApplyDTO dto) {
        ScoreAccountDTO accountDTO = new ScoreAccountDTO();
        accountDTO.setMerchantId(dto.getMerchantId());
        accountDTO.setAmount(dto.getAmount());
        accountDTO.setChargeType(ChargeType.WITHDRAW);
        String tradeNo = this.generateWithdrawNo();
        accountDTO.setTradeNo(tradeNo);
        this.updateAccount(accountDTO);
        this.withdrawSuccess(tradeNo);
    }

    @Override
    public void withdrawSuccess(String tradeNo) {
        ScoreAccountLog accountLog = this.getByTradeNo(tradeNo);
        ScoreAccount account = this.getAccount(accountLog.getMerchantId());
        account.setWithdrawFreeze(account.getWithdrawFreeze() - accountLog.getAmount());
        this.updateById(account);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(accountLog.getAmount());
        accountDTO.setAccountType(AccountType.SCORE_WITHDRAW);
        accountDTO.setMerchantId(accountLog.getMerchantId());
        accountDTO.setTradeNo(tradeNo);
        accountService.updateAccount(accountDTO);
    }

    @Override
    public void withdrawFail(String tradeNo) {
        ScoreAccountLog accountLog = this.getByTradeNo(tradeNo);
        ScoreAccountDTO accountDTO = new ScoreAccountDTO();
        accountDTO.setAmount(accountLog.getAmount());
        accountDTO.setTradeNo(tradeNo);
        accountDTO.setMerchantId(accountLog.getMerchantId());
        accountDTO.setChargeType(ChargeType.WITHDRAW_FAIL);
        this.updateAccount(accountDTO);
    }

    @Override
    public void rechargeBalance(ScoreRechargeDTO dto) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setMerchantId(dto.getMerchantId());
        accountDTO.setAmount(dto.getAmount());
        accountDTO.setAccountType(AccountType.SCORE_RECHARGE);
        String tradeNo = this.generateRechargeNo();
        accountDTO.setTradeNo(tradeNo);
        accountService.updateAccount(accountDTO);
        this.rechargeBalanceSuccess(tradeNo);
    }

    @Override
    public PrepayVO rechargeScan(ScoreScanRechargeDTO dto) {
        PrepayDTO prepayDTO = new PrepayDTO();
        prepayDTO.setAmount(dto.getAmount());
        prepayDTO.setClientIp(dto.getClientIp());
        prepayDTO.setDescription(String.format(SCORE_RECHARGE_GOOD_TITLE, DecimalUtil.centToYuan(dto.getAmount())));
        String tradeNo = this.generateRechargeNo();
        prepayDTO.setTradeNo(tradeNo);
        // 默认传递交易单号
        prepayDTO.setAttach(tradeNo);
        // 微信扫码支付/支付宝当面付
        if (dto.getPayChannel() == PayChannel.WECHAT) {
            prepayDTO.setTradeType(TradeType.WECHAT_NATIVE);
        } else {
            prepayDTO.setTradeType(TradeType.ALI_FACE_PAY);
        }
        PrepayVO vo = aggregatePayService.createPrepay(prepayDTO);
        ScanRechargeLog rechargeLog = new ScanRechargeLog();
        rechargeLog.setAmount(dto.getAmount());
        rechargeLog.setTradeType(prepayDTO.getTradeType());
        rechargeLog.setTradeNo(tradeNo);
        rechargeLog.setMerchantId(dto.getMerchantId());
        rechargeLog.setState(0);
        rechargeLog.setQrCode(vo.getQrCodeUrl());
        scanRechargeLogMapper.insert(rechargeLog);
        return vo;
    }

    @Override
    public void rechargeScanSuccess(Long merchantId, Integer amount, String tradeNo) {
        ScoreAccountDTO accountDTO = new ScoreAccountDTO();
        accountDTO.setMerchantId(merchantId);
        accountDTO.setAmount(amount);
        accountDTO.setChargeType(ChargeType.RECHARGE);
        accountDTO.setTradeNo(tradeNo);
        this.updateAccount(accountDTO);
    }

    /**
     * 积分充值成功 (一般在支付成功回调中调用该方法)
     *
     * @param tradeNo 交易单号
     */
    private void rechargeBalanceSuccess(String tradeNo) {
        AccountLog accountLog = accountLogService.getByTradeNo(tradeNo);
        ScoreAccountDTO accountDTO = new ScoreAccountDTO();
        accountDTO.setMerchantId(accountLog.getMerchantId());
        accountDTO.setAmount(accountLog.getAmount());
        accountDTO.setChargeType(ChargeType.RECHARGE);
        accountDTO.setTradeNo(tradeNo);
        this.updateAccount(accountDTO);
    }

    /**
     * 根据交易单号查询积分变动记录
     *
     * @param tradeNo 交易单号
     * @return 积分变动记录
     */
    private ScoreAccountLog getByTradeNo(String tradeNo) {
        LambdaQueryWrapper<ScoreAccountLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScoreAccountLog::getTradeNo, tradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        ScoreAccountLog scoreAccountLog = scoreAccountLogMapper.selectOne(wrapper);
        if (scoreAccountLog == null) {
            log.error("积分变动记录未查询到 [{}]", tradeNo);
            alarmService.sendMsg(String.format("资金变动记录不存在 [%s]", tradeNo));
            throw new BusinessException(ErrorCode.SCORE_LOG_NULL);
        }
        return scoreAccountLog;
    }

    /**
     * 提现校验单号
     *
     * @return 单号信息
     */
    private String generateWithdrawNo() {
        return SCORE_WITHDRAW_PREFIX + IdWorker.getIdStr();
    }

    /**
     * 提现校验单号
     *
     * @return 单号信息
     */
    private String generateRechargeNo() {
        return SCORE_RECHARGE_PREFIX + IdWorker.getIdStr();
    }

    /**
     * 更新积分账户
     *
     * @param account 账户信息
     */
    private void updateById(ScoreAccount account) {
        if (account.getAmount() < 0) {
            log.error("账户可用积分余额不足 [{}]", account);
            alarmService.sendMsg(String.format("账户可用积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_USE);
        }
        if (account.getPayFreeze() < 0) {
            log.error("账户支付积分余额不足 [{}]", account);
            alarmService.sendMsg(String.format("账户支付积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_USE);
        }
        if (account.getWithdrawFreeze() < 0) {
            log.error("账户提现积分余额不足 [{}]", account);
            alarmService.sendMsg(String.format("账户提现积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_WITHDRAW);
        }
        int update = scoreAccountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}]", account);
            alarmService.sendMsg(String.format("更新商户账户失败 [%s]", account));
            throw new BusinessException(ErrorCode.SCORE_ACCOUNT_UPDATE);
        }
    }
}
