package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.AlarmService;
import com.eghm.common.JsonService;
import com.eghm.configuration.SystemProperties;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.dto.business.freeze.AccountFreezeDTO;
import com.eghm.dto.business.freeze.RefundChangeDTO;
import com.eghm.enums.AccountType;
import com.eghm.enums.ChangeType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AccountLogMapper;
import com.eghm.mapper.AccountMapper;
import com.eghm.mapper.MerchantMapper;
import com.eghm.model.*;
import com.eghm.service.business.AccountFreezeLogService;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * <p>
 * 商户账户信息表 服务实现类
 * 1. 支付时:  增加冻结记录
 * 2. 订单完成: 更新资金账户 + 资金变动记录
 * 3. 提现申请: 更新资金账户 + 资金变动记录 + 提现记录
 * 4. 提现成功: 更新资金账户
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */

@Slf4j
@AllArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService, MerchantInitService {

    private final JsonService jsonService;

    private final AlarmService alarmService;

    private final AccountMapper accountMapper;

    private final MerchantMapper merchantMapper;

    private final AccountLogMapper accountLogMapper;

    private final SystemProperties systemProperties;

    private final AccountFreezeLogService accountFreezeLogService;

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return true;
    }

    @Override
    public void init(Merchant merchant) {
        Account account = new Account();
        account.setMerchantId(merchant.getId());
        accountMapper.insert(account);
    }

    @Override
    public void updateAccount(AccountDTO dto) {
        log.info("开始更新资金账户 [{}]", jsonService.toJson(dto));
        if (dto.getAmount() <= 0) {
            // 金额小于0不做任何操作
            return;
        }
        boolean saveLog = true;
        Account account = this.getAccount(dto.getMerchantId());
        if (dto.getAccountType() == AccountType.ORDER_PAY) {
            // 支付回调中调用该方法
            account.setPayFreeze(account.getPayFreeze() + dto.getAmount());
        } else if (dto.getAccountType() == AccountType.ORDER_REFUND) {
            // 退款回调时调用该方法
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
        } else if (dto.getAccountType() == AccountType.WITHDRAW_APPLY) {
            // 提现申请时调用该方法
            account.setAmount(account.getAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
        } else if (dto.getAccountType() == AccountType.SCORE_RECHARGE) {
            // 在积分充值申请时调用该方法
            account.setAmount(account.getAmount() - dto.getAmount());
        } else if (dto.getAccountType() == AccountType.SCORE_WITHDRAW) {
            // 需要在积分提现异步处理中调用该方法
            account.setAmount(account.getAmount() + dto.getAmount());
        } else if (dto.getAccountType() == AccountType.WITHDRAW_SUCCESS) {
            // 在提现成功异步处理中调用该方法
            account.setWithdrawFreeze(account.getWithdrawFreeze() - dto.getAmount());
            saveLog = false;
        } else if (dto.getAccountType() == AccountType.WITHDRAW_FAIL) {
            // 在提现失败异步处理中调用该方法
            account.setWithdrawFreeze(account.getWithdrawFreeze() - dto.getAmount());
            account.setAmount(account.getAmount() + dto.getAmount());
        }
        this.updateById(account);
        if (saveLog) {
            AccountLog accountLog = DataUtil.copy(dto, AccountLog.class);
            accountLog.setDirection(accountLog.getAccountType().getDirection());
            accountLog.setSurplusAmount(account.getAmount() + account.getPayFreeze());
            accountLogMapper.insert(accountLog);
        }
    }

    @Override
    public void orderComplete(Long merchantId, String orderNo) {
        AccountFreezeLog freezeLog = accountFreezeLogService.complete(merchantId, orderNo);
        Account account = this.getAccount(merchantId);
        account.setAmount(account.getAmount() + freezeLog.getAmount());
        account.setAmount(account.getPayFreeze() - freezeLog.getAmount());
        this.updateById(account);
    }

    @Override
    public void withdrawApply(Long merchantId, Integer amount) {
        Account account = this.getAccount(merchantId);
        account.setWithdrawFreeze(account.getWithdrawFreeze() - amount);
        this.updateById(account);
    }

    @Override
    public void withdrawFail(Long merchantId, Integer amount) {
        Account account = this.getAccount(merchantId);
        account.setAmount(account.getAmount() + amount);
        account.setWithdrawFreeze(account.getWithdrawFreeze() - amount);
        this.updateById(account);
    }

    @Override
    public Account getAccount(Long merchantId) {
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Account::getMerchantId, merchantId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        Account account = accountMapper.selectOne(wrapper);
        if (account == null) {
            log.error("账户信息不存在 [{}]", merchantId);
            throw new BusinessException(ErrorCode.ACCOUNT_NOT_EXIST);
        }
        return account;
    }

    @Override
    public void paySuccessAddFreeze(Order order) {
        if (order.getPayAmount() <= 0) {
            log.info("支付金额小于等于0,不更新资金账户 [{}]", order.getOrderNo());
            return;
        }
        Merchant merchant = this.selectByIdRequired(order.getMerchantId());
        Integer actualAmount = this.calcActualAmount(order.getPayAmount(), merchant.getPlatformServiceRate());
        this.paySuccessUpdateAccount(order.getMerchantId(), actualAmount, order.getOrderNo(), order.getTradeNo());
        // 添加平台手续费账户更新
        int serviceFee = order.getPayAmount()  - actualAmount;
        if (serviceFee > 0 ) {
            this.paySuccessUpdateAccount(systemProperties.getPlatformMerchantId(), serviceFee, order.getOrderNo(), order.getTradeNo());
        } else {
            log.info("支付没有产生平台手续费,不更新平台账户 [{}] [{}]", order.getOrderNo(), serviceFee);
        }
    }

    @Override
    public void refundSuccessUpdateFreeze(Order order, Integer refundAmount, String refundNo) {
        Merchant merchant = this.selectByIdRequired(order.getMerchantId());
        // 表示最后一次退款
        Integer actualAmount;
        if (order.getPayAmount().equals(order.getRefundAmount())) {
            Integer serviceAmount = this.calcServiceAmount(refundAmount, merchant.getPlatformServiceRate());
            actualAmount = refundAmount - serviceAmount;
            log.info("最后一次退款,产生的平台手续费 [{}] [{}] [{}] [{}]", order.getOrderNo(), refundNo, refundAmount, serviceAmount);
        } else {
            actualAmount = this.calcActualAmount(refundAmount, merchant.getPlatformServiceRate());
        }
        this.refundSuccessUpdateAccount(order.getMerchantId(), actualAmount, order.getOrderNo(), refundNo);
        int serviceFee = refundAmount - actualAmount;
        // 添加平台手续费账户更新
        if (serviceFee > 0) {
            this.refundSuccessUpdateAccount(systemProperties.getPlatformMerchantId(), serviceFee, order.getOrderNo(), refundNo);
        } else {
            log.info("退款没有产生平台手续费,不更新平台账户 [{}] [{}]", order.getOrderNo(), serviceFee);
        }
    }

    /**
     * 根据id查询商户
     *
     * @param id id
     * @return 商户信息
     */
    public Merchant selectByIdRequired(Long id) {
        Merchant merchant = merchantMapper.selectById(id);
        if (merchant == null) {
            log.error("商户信息不存在 [{}]", id);
            throw new BusinessException(ErrorCode.MERCHANT_NULL);
        }
        return merchant;
    }

    /**
     * 支付成功更新冻结账户
     *
     * @param merchantId 商户id
     * @param amount 金额
     * @param orderNo orderNo
     * @param tradeNo tradeNo
     */
    private void paySuccessUpdateAccount(Long merchantId, Integer amount, String orderNo, String tradeNo) {
        if (amount <= 0) {
            log.info("支付成功更新冻结账户,金额为0,不更新冻结账户 [{}] [{}] [{}]", merchantId, orderNo, tradeNo);
            return;
        }
        AccountDTO dto = new AccountDTO();
        dto.setMerchantId(merchantId);
        dto.setAmount(amount);
        dto.setAccountType(AccountType.ORDER_PAY);
        dto.setTradeNo(tradeNo);
        this.updateAccount(dto);
        AccountFreezeDTO freezeDTO = new AccountFreezeDTO();
        freezeDTO.setMerchantId(merchantId);
        freezeDTO.setAmount(amount);
        freezeDTO.setChangeType(ChangeType.PAY_FREEZE);
        freezeDTO.setOrderNo(orderNo);
        accountFreezeLogService.addFreezeLog(freezeDTO);
    }

    /**
     * 退款成功更新冻结账户
     *
     * @param merchantId 商户id
     * @param amount 金额
     * @param orderNo orderNo
     * @param refundNo 退款流水号
     */
    private void refundSuccessUpdateAccount(Long merchantId, Integer amount, String orderNo, String refundNo) {
        if (amount <= 0) {
            log.info("退款成功解冻账户,金额为0,不更新冻结账户 [{}] [{}] [{}]", merchantId, orderNo, refundNo);
            return;
        }
        AccountDTO dto = new AccountDTO();
        dto.setMerchantId(merchantId);
        dto.setAmount(amount);
        dto.setAccountType(AccountType.ORDER_REFUND);
        dto.setTradeNo(refundNo);
        this.updateAccount(dto);
        RefundChangeDTO refundDTO = new RefundChangeDTO();
        refundDTO.setMerchantId(merchantId);
        refundDTO.setRefundAmount(amount);
        refundDTO.setOrderNo(orderNo);
        accountFreezeLogService.refund(refundDTO);
    }

    /**
     * 计算实际应收金额(用户实付金额-平台服务费)
     *
     * @param amount  金额
     * @param serviceRate 平台服务费率 单位:%
     * @return 实付金额
     */
    private Integer calcActualAmount(Integer amount, BigDecimal serviceRate) {
        BigDecimal subtract = BigDecimal.valueOf(100).subtract(serviceRate);
        return subtract.multiply(BigDecimal.valueOf(amount)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN).intValue();
    }

    /**
     * 计算实际应收金额(用户实付金额-平台服务费)
     *
     * @param amount  金额
     * @param serviceRate 平台服务费率 单位:%
     * @return 实付金额
     */
    private Integer calcServiceAmount(Integer amount, BigDecimal serviceRate) {
        return serviceRate.multiply(BigDecimal.valueOf(amount)).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_DOWN).intValue();
    }

    /**
     * 更新账户信息
     * @param account 账户信息
     */
    private void updateById(Account account) {
        this.checkAccount(account);
        int update = accountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}]", account);
            alarmService.sendMsg(String.format("更新商户账户失败 [%s]", account));
            throw new BusinessException(ErrorCode.ACCOUNT_UPDATE);
        }
    }

    /**
     * 校验账户余额信息
     *
     * @param account 账户
     */
    private void checkAccount(Account account) {
        if (account.getAmount() < 0) {
            log.error("商户可用余额不足 [{}]", account);
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_USE);
        }
        if (account.getPayFreeze() < 0) {
            log.error("账户支付冻结余额不足 [{}]", account);
            alarmService.sendMsg(String.format("账户支付冻结余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_PAY);
        }
        if (account.getWithdrawFreeze() < 0) {
            log.error("账户提现冻结余额不足 [{}]", account);
            alarmService.sendMsg(String.format("账户提现冻结余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_WITHDRAW);
        }
    }
}
