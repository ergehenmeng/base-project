package com.eghm.service.business.impl;

import com.eghm.mapper.AccountMapper;
import com.eghm.service.business.AccountService;
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
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

}
