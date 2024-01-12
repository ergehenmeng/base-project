package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.mapper.AccountLogMapper;
import com.eghm.mapper.AccountMapper;
import com.eghm.model.Account;
import com.eghm.model.Merchant;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.MerchantInitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    @Override
    public void updateAccount(AccountDTO dto) {

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
}
