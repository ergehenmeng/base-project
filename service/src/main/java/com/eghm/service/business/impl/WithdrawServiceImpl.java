package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.AccountDTO;
import com.eghm.dto.business.withdraw.WithdrawApplyDTO;
import com.eghm.dto.business.withdraw.WithdrawQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ref.AccountType;
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
        dto.setAccountType(AccountType.WITHDRAW);
        String tradeNo = this.generateWithdrawNo();
        dto.setTradeNo(tradeNo);
        ExceptionUtil.transfer(() -> accountService.updateAccount(dto), ErrorCode.MERCHANT_ACCOUNT_USE, ErrorCode.WITHDRAW_ENOUGH);

        WithdrawLog withdrawLog = DataUtil.copy(apply, WithdrawLog.class);
        withdrawLog.setWithdrawWay(1);
        withdrawLog.setState(0);
        withdrawLog.setCreateTime(LocalDateTime.now());
        withdrawLog.setRefundNo(tradeNo);
        withdrawLogMapper.insert(withdrawLog);
        accountService.withdrawSuccess(apply.getMerchantId(), apply.getAmount());
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
