package com.eghm.service.business.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.AccountType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.WithdrawLogMapper;
import com.eghm.model.Account;
import com.eghm.model.WithdrawLog;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.WithdrawService;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.eghm.constant.CommonConstant.WITHDRAW_PREFIX;

/**
 * <p>
 * 商户提现记录 服务实现类, 待实现开户,绑卡,支付,分账,提现等逻辑
 * (不走微信分账那一套, 走银行聚合支付)
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-16
 */
@Slf4j
@AllArgsConstructor
@Service("withdrawService")
public class WithdrawServiceImpl implements WithdrawService {

    private final WithdrawLogMapper withdrawLogMapper;

    private final AccountService accountService;

    @Override
    public Page<WithdrawLog> getByPage(WithdrawQueryRequest request) {
        LambdaQueryWrapper<WithdrawLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getMerchantId() != null, WithdrawLog::getMerchantId, request.getMerchantId());
        wrapper.eq(request.getState() != null, WithdrawLog::getState, request.getState());
        wrapper.eq(request.getWithdrawWay() != null, WithdrawLog::getWithdrawWay, request.getWithdrawWay());
        wrapper.ge(request.getStartDate() != null, WithdrawLog::getCreateTime, request.getStartDate());
        wrapper.lt(request.getEndDate() != null, WithdrawLog::getCreateTime, request.getEndDate());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper -> queryWrapper.like(WithdrawLog::getTradeNo, request.getQueryName()).or().like(WithdrawLog::getOutTradeNo, request.getQueryName()));
        wrapper.orderByDesc(WithdrawLog::getId);
        return withdrawLogMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public void apply(WithdrawApplyDTO apply) {
        Account account = accountService.getAccount(apply.getMerchantId());
        if (account.getWithdrawAmount() < apply.getAmount()) {
            log.error("商户可提现金额不足 [{}] [{}] [{}]", apply.getMerchantId(), apply.getAmount(), account.getWithdrawAmount());
            throw new BusinessException(ErrorCode.WITHDRAW_ENOUGH);
        }
        AccountDTO dto = new AccountDTO();
        dto.setMerchantId(apply.getMerchantId());
        dto.setAmount(apply.getAmount());
        dto.setAccountType(AccountType.WITHDRAW);
        accountService.updateAccount(dto);

        WithdrawLog withdrawLog = DataUtil.copy(apply, WithdrawLog.class);
        withdrawLog.setWithdrawWay(1);
        withdrawLog.setState(0);
        withdrawLog.setCreateTime(LocalDateTime.now());
        String tradeNo = this.generateWithdrawNo();
        withdrawLog.setTradeNo(tradeNo);
        withdrawLogMapper.insert(withdrawLog);
        // TODO 计算手续费 + 发起提现
    }

    /**
     * 提现校验单号
     *
     * @return 单号信息
     */
    private String generateWithdrawNo() {
        return WITHDRAW_PREFIX + IdWorker.getIdStr();
    }

}
