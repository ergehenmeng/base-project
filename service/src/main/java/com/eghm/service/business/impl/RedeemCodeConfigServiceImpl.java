package com.eghm.service.business.impl;

import com.eghm.model.RedeemCodeConfig;
import com.eghm.mapper.RedeemCodeConfigMapper;
import com.eghm.service.business.RedeemCodeConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 兑换码配置表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-02-10
 */
@Slf4j
@AllArgsConstructor
@Service("redeemCodeConfigService")
public class RedeemCodeConfigServiceImpl implements RedeemCodeConfigService {

    private final RedeemCodeConfigMapper redeemCodeConfigMapper;
}
