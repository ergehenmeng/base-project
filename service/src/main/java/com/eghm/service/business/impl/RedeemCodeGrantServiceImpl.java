package com.eghm.service.business.impl;

import com.eghm.mapper.RedeemCodeGrantMapper;
import com.eghm.model.RedeemCodeConfig;
import com.eghm.service.business.RedeemCodeGrantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 兑换码发放表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Slf4j
@AllArgsConstructor
@Service("redeemCodeGrantService")
public class RedeemCodeGrantServiceImpl implements RedeemCodeGrantService {

    private final RedeemCodeGrantMapper redeemCodeGrantMapper;
}
