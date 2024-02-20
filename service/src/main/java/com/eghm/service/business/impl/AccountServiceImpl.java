package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.AccountType;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AccountLogMapper;
import com.eghm.mapper.AccountMapper;
import com.eghm.model.Account;
import com.eghm.model.AccountLog;
import com.eghm.model.Merchant;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    private final AccountMapper accountMapper;

    private final AccountLogMapper accountLogMapper;

    private final DingTalkService dingTalkService;

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
        Account account = this.getAccount(dto.getMerchantId());
        if (dto.getAccountType() == AccountType.ORDER_PAY) {
            account.setPayFreeze(account.getPayFreeze() + dto.getAmount());
        } else if (dto.getAccountType() == AccountType.ORDER_REFUND) {
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
        } else if (dto.getAccountType() == AccountType.WITHDRAW) {
            account.setAmount(account.getAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
        } else if (dto.getAccountType() == AccountType.SCORE_RECHARGE) {
            account.setAmount(account.getAmount() - dto.getAmount());
        } else if (dto.getAccountType() == AccountType.SCORE_WITHDRAW) {
            account.setAmount(account.getAmount() + dto.getAmount());
        }
        this.updateById(account);

        AccountLog accountLog = DataUtil.copy(dto, AccountLog.class);
        accountLog.setDirection(accountLog.getAccountType().getDirection());
        accountLog.setSurplusAmount(account.getAmount() + account.getPayFreeze());
        accountLogMapper.insert(accountLog);
    }

    @Override
    public void orderComplete(Long merchantId, Integer amount) {
        Account account = this.getAccount(merchantId);
        account.setAmount(account.getAmount() + amount);
        account.setAmount(account.getPayFreeze() - amount);
        this.updateById(account);
    }

    @Override
    public void withdrawSuccess(Long merchantId, Integer amount) {
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

    /**
     * 更新账户信息
     * @param account 账户信息
     */
    private void updateById(Account account) {
        this.checkAccount(account);
        int update = accountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}]", account);
            dingTalkService.sendMsg(String.format("更新商户账户失败 [%s]", account));
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
            dingTalkService.sendMsg(String.format("账户支付冻结余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_PAY);
        }
        if (account.getWithdrawFreeze() < 0) {
            log.error("账户提现冻结余额不足 [{}]", account);
            dingTalkService.sendMsg(String.format("账户提现冻结余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_WITHDRAW);
        }
    }
}
