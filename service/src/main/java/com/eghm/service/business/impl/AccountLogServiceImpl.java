package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.business.account.AccountQueryRequest;
import com.eghm.mapper.AccountLogMapper;
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

    private final AccountLogMapper accountLogMapper;

    @Override
    public Page<AccountLogResponse> getByPage(AccountQueryRequest request) {
        return accountLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public List<AccountLogResponse> getList(AccountQueryRequest request) {
        Page<AccountLogResponse> byPage = accountLogMapper.getByPage(request.createPage(false), request);
        return byPage.getRecords();
    }
}
