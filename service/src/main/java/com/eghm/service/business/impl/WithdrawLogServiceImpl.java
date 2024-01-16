package com.eghm.service.business.impl;

import com.eghm.mapper.WithdrawLogMapper;
import com.eghm.service.business.WithdrawLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
@Service("withdrawLogService")
public class WithdrawLogServiceImpl implements WithdrawLogService {

    private final WithdrawLogMapper withdrawLogMapper;
}
