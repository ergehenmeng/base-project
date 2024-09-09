package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.AlarmService;
import com.eghm.constant.CommonConstant;
import com.eghm.dto.business.account.AccountQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.AccountLogMapper;
import com.eghm.model.AccountLog;
import com.eghm.service.business.AccountLogService;
import com.eghm.vo.business.account.AccountLogResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商户资金变动明细表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Slf4j
@AllArgsConstructor
@Service("accountLogService")
public class AccountLogServiceImpl implements AccountLogService {

    private final AlarmService alarmService;

    private final AccountLogMapper accountLogMapper;

    @Override
    public Page<AccountLogResponse> getByPage(AccountQueryRequest request) {
        return accountLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<AccountLogResponse> getList(AccountQueryRequest request) {
        Page<AccountLogResponse> byPage = accountLogMapper.getByPage(request.createNullPage(), request);
        return byPage.getRecords();
    }

    @Override
    public AccountLog getByTradeNo(String tradeNo) {
        LambdaQueryWrapper<AccountLog> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AccountLog::getTradeNo, tradeNo);
        wrapper.last(CommonConstant.LIMIT_ONE);
        AccountLog accountLog = accountLogMapper.selectOne(wrapper);
        if (accountLog == null) {
            log.error("资金变动记录未查询到 [{}]", tradeNo);
            alarmService.sendMsg(String.format("资金变动记录不存在 [%s]", tradeNo));
            throw new BusinessException(ErrorCode.ACCOUNT_LOG_NULL);
        }
        return accountLog;
    }
}
