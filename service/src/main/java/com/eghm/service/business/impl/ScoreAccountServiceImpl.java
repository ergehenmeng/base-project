package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.RoleType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.ScoreAccountLogMapper;
import com.eghm.mapper.ScoreAccountMapper;
import com.eghm.model.Merchant;
import com.eghm.model.ScoreAccount;
import com.eghm.model.ScoreAccountLog;
import com.eghm.service.business.MerchantInitService;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.DataUtil;
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

    private final DingTalkService dingTalkService;

    private final ScoreAccountMapper scoreAccountMapper;

    private final ScoreAccountLogMapper scoreAccountLogMapper;

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
        ScoreAccount account = this.getAccount(dto.getMerchantId());
        ScoreAccountLog accountLog = DataUtil.copy(dto, ScoreAccountLog.class);
        if (dto.getChargeType() == ChargeType.RECHARGE) {
            account.setAmount(account.getAmount() + dto.getAmount());
            accountLog.setDirection(1);
        } else if (dto.getChargeType() == ChargeType.ORDER) {
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
            account.setAmount(account.getAmount() + dto.getAmount());
            accountLog.setDirection(1);
        } else if (dto.getChargeType() == ChargeType.DRAW || dto.getChargeType() == ChargeType.ATTENTION_GIFT) {
            account.setAmount(account.getAmount() - dto.getAmount());
            accountLog.setDirection(2);
        } else if (dto.getChargeType() == ChargeType.WITHDRAW) {
            account.setAmount(account.getAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
            accountLog.setDirection(2);
        }

        if (account.getPayFreeze() < 0 || account.getWithdrawFreeze() < 0 || account.getAmount() < 0) {
            log.error("账户积分余额信息异常 [{}]", account);
            dingTalkService.sendMsg(String.format("账户积分余额信息异常 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_ACCOUNT);
        }
        int update = scoreAccountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}] [{}]", dto, account);
            dingTalkService.sendMsg(String.format("更新商户账户失败 [%s]", dto));
            throw new BusinessException(ErrorCode.ACCOUNT_UPDATE);
        }
        scoreAccountLogMapper.insert(accountLog);
        accountLog.setSurplusAmount(account.getAmount() + account.getPayFreeze());

    }

    @Override
    public ScoreAccount getAccount(Long merchantId) {
        LambdaQueryWrapper<ScoreAccount> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ScoreAccount::getMerchantId, merchantId);
        wrapper.last(CommonConstant.LIMIT_ONE);
        return scoreAccountMapper.selectOne(wrapper);
    }

}
