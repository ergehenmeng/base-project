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
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 商户账户信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */

@Slf4j
@AllArgsConstructor
@Service("accountService")
public class AccountServiceImpl implements AccountService, MerchantInitService {

    private final AccountMapper accountMapper;

    private final AccountLogMapper accountLogMapper;

    private final DingTalkService dingTalkService;

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return true;
    }

    @Override
    public void updateAccount(AccountDTO dto) {
        Account account = this.getAccount(dto.getMerchantId());
        AccountLog accountLog = DataUtil.copy(dto, AccountLog.class);
        if (dto.getAccountType() == AccountType.PAY_INCOME) {
            account.setPayFreeze(account.getPayFreeze() + dto.getAmount());
            accountLog.setSurplusAmount(account.getPayFreeze() + account.getWithdrawAmount());
        } else if (dto.getAccountType() == AccountType.REFUND_DISBURSE) {
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
            accountLog.setSurplusAmount(account.getPayFreeze() + account.getWithdrawAmount());
        } else if (dto.getAccountType() == AccountType.WITHDRAW_DISBURSE) {
            account.setWithdrawAmount(account.getWithdrawAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
            accountLog.setSurplusAmount(account.getPayFreeze() + account.getWithdrawAmount());
        }
        this.checkAccount(account);
        accountLogMapper.insert(accountLog);

        account.setUpdateTime(LocalDateTime.now());
        int update = accountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}] [{}]", dto, account);
            dingTalkService.sendMsg(String.format("更新商户账户失败 [%s]", dto));
            throw new BusinessException(ErrorCode.ACCOUNT_UPDATE);
        }
    }

    @Override
    public Account getAccount(Long merchantId) {
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Account::getMerchantId, merchantId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return accountMapper.selectOne(wrapper);
    }

    @Override
    public void init(Merchant merchant) {
        Account account = new Account();
        account.setMerchantId(merchant.getId());
        accountMapper.insert(account);
    }

    /**
     * 校验账户余额信息
     *
     * @param account 账户
     */
    private void checkAccount(Account account) {
        if (account.getPayFreeze() < 0 || account.getWithdrawFreeze() < 0 || account.getWithdrawAmount() < 0) {
            log.error("账户余额信息异常 [{}]", account);
            dingTalkService.sendMsg(String.format("账户余额信息异常 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_ACCOUNT_ERROR);
        }
    }
}
