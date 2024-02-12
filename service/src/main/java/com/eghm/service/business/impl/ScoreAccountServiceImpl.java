package com.eghm.service.business.impl;

import com.eghm.enums.ref.RoleType;
import com.eghm.mapper.ScoreAccountMapper;
import com.eghm.model.Merchant;
import com.eghm.model.ScoreAccount;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.ScoreAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private final ScoreAccountMapper scoreAccountMapper;

    @Override
    public boolean support(List<RoleType> roleTypes) {
        return roleTypes.contains(RoleType.ITEM);
    }

    @Override
    public void init(Merchant merchant) {
        ScoreAccount account = new ScoreAccount();
        account.setMerchantId(merchant.getId());
        scoreAccountMapper.insert(account);
    }
}
