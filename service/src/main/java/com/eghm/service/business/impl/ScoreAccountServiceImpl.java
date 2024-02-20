package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.dto.business.withdraw.score.ScoreWithdrawApplyDTO;
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
import com.eghm.service.common.JsonService;
import com.eghm.service.sys.DingTalkService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.constant.CommonConstant.SCORE_WITHDRAW_PREFIX;

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
        log.info("更新商户积分账户 [{}]", jsonService.toJson(dto));
        ScoreAccount account = this.getAccount(dto.getMerchantId());
        if (dto.getChargeType() == ChargeType.RECHARGE) {
            account.setAmount(account.getAmount() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.ORDER_PAY) {
            account.setPayFreeze(account.getPayFreeze() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.ORDER_REFUND) {
            account.setPayFreeze(account.getPayFreeze() - dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.DRAW || dto.getChargeType() == ChargeType.ATTENTION_GIFT) {
            account.setAmount(account.getAmount() - dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.WITHDRAW) {
            account.setAmount(account.getAmount() - dto.getAmount());
            account.setWithdrawFreeze(account.getWithdrawFreeze() + dto.getAmount());
        } else if (dto.getChargeType() == ChargeType.WITHDRAW_FAIL) {
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
        String tradeNo = generateWithdrawNo();
        accountDTO.setTradeNo(tradeNo);
        this.updateAccount(accountDTO);
        // TODO 将平台账户转到商户账户 tradeNo 异步需要在回调中处理
    }

    @Override
    public void withdrawSuccess(String tradeNo) {
        ScoreAccountLog accountLog = this.getByTradeNo(tradeNo);
        ScoreAccount account = this.getAccount(accountLog.getMerchantId());
        account.setWithdrawFreeze(account.getWithdrawFreeze() - accountLog.getAmount());
        this.updateById(account);
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
            dingTalkService.sendMsg(String.format("提现单号不存在 [%s]", tradeNo));
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
     * 更新积分账户
     *
     * @param account 账户信息
     */
    private void updateById(ScoreAccount account) {
        if (account.getAmount() < 0) {
            log.error("账户可用积分余额不足 [{}]", account);
            dingTalkService.sendMsg(String.format("账户可用积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_USE);
        }
        if (account.getPayFreeze() < 0) {
            log.error("账户支付积分余额不足 [{}]", account);
            dingTalkService.sendMsg(String.format("账户支付积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_USE);
        }
        if (account.getWithdrawFreeze() < 0) {
            log.error("账户提现积分余额不足 [{}]", account);
            dingTalkService.sendMsg(String.format("账户提现积分余额不足 [%s]", account));
            throw new BusinessException(ErrorCode.MERCHANT_SCORE_WITHDRAW);
        }

        int update = scoreAccountMapper.updateAccount(account);
        if (update != 1) {
            log.error("更新账户信息失败 [{}]", account);
            dingTalkService.sendMsg(String.format("更新商户账户失败 [%s]", account));
            throw new BusinessException(ErrorCode.SCORE_ACCOUNT_UPDATE);
        }
    }
}
