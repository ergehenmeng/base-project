package com.eghm.service.business.impl;

import com.eghm.mapper.AccountLogMapper;
import com.eghm.service.business.AccountLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
