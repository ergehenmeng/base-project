package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.constants.CommonConstant;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.dto.business.account.WithdrawNotifyDTO;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.enums.AccountType;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.WithdrawState;
import com.eghm.enums.WithdrawWay;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.WithdrawLogMapper;
import com.eghm.model.WithdrawLog;
import com.eghm.service.business.AccountService;
import com.eghm.service.business.WithdrawService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.ExceptionUtil;
import com.eghm.vo.business.withdraw.WithdrawLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.eghm.constants.CommonConstant.WITHDRAW_PREFIX;

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

    private final AccountService accountService;

    private final WithdrawLogMapper withdrawLogMapper;

    @Override
    public Page<WithdrawLogResponse> getByPage(WithdrawQueryRequest request) {
        return withdrawLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<WithdrawLogResponse> getList(WithdrawQueryRequest request) {
        return withdrawLogMapper.getByPage(request.createNullPage(), request).getRecords();
    }

    @Override
    public void apply(WithdrawApplyDTO apply) {
        AccountDTO dto = new AccountDTO();
        dto.setMerchantId(apply.getMerchantId());
        dto.setAmount(apply.getAmount());
        dto.setAccountType(AccountType.WITHDRAW_APPLY);
        String tradeNo = this.generateWithdrawNo();
        dto.setTradeNo(tradeNo);
        ExceptionUtil.transfer(() -> accountService.updateAccount(dto), ErrorCode.MERCHANT_ACCOUNT_USE, ErrorCode.WITHDRAW_ENOUGH);
        WithdrawLog withdrawLog = DataUtil.copy(apply, WithdrawLog.class);
        withdrawLog.setWithdrawWay(WithdrawWay.MANUAL);
        withdrawLog.setState(WithdrawState.APPLY);
        withdrawLog.setCreateTime(LocalDateTime.now());
        withdrawLog.setRefundNo(tradeNo);
        withdrawLogMapper.insert(withdrawLog);
    }

    @Override
    public void notify(WithdrawNotifyDTO dto) {
        LambdaQueryWrapper<WithdrawLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(WithdrawLog::getRefundNo, dto.getTradeNo());
        wrapper.last(CommonConstant.LIMIT_ONE);
        WithdrawLog withdrawLog = withdrawLogMapper.selectOne(wrapper);
        if (withdrawLog == null) {
            log.error("未找到对应的提现记录 [{}]", dto.getTradeNo());
            throw new BusinessException(ErrorCode.WITHDRAW_LOG_NULL);
        }
        if (withdrawLog.getState() != WithdrawState.APPLY) {
            log.warn("提现记录状态异常, 订单已更新 [{}] [{}] [{}]", dto.getTradeNo(), dto.getState(), withdrawLog.getState());
            return;
        }
        withdrawLog.setState(dto.getState());
        withdrawLog.setOutRefundNo(dto.getOutRefundNo());
        withdrawLog.setPaymentTime(dto.getWithdrawTime());
        withdrawLogMapper.updateById(withdrawLog);
        AccountDTO account = new AccountDTO();
        account.setMerchantId(withdrawLog.getMerchantId());
        account.setAmount(withdrawLog.getAmount());
        account.setTradeNo(withdrawLog.getRefundNo());
        if (dto.getState() == WithdrawState.SUCCESS) {
            account.setAccountType(AccountType.WITHDRAW_SUCCESS);
        } else {
            account.setAccountType(AccountType.WITHDRAW_FAIL);
        }
        accountService.updateAccount(account);
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
