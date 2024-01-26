package com.eghm.service.business.impl;

import com.eghm.dto.business.purchase.LimitPurchaseAddRequest;
import com.eghm.dto.business.purchase.LimitPurchaseEditRequest;
import com.eghm.mapper.LimitPurchaseMapper;
import com.eghm.service.business.LimitPurchaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-26
 */

@Slf4j
@AllArgsConstructor
@Service("limitPurchaseService")
public class LimitPurchaseServiceImpl implements LimitPurchaseService {

    private final LimitPurchaseMapper limitPurchaseMapper;

    @Override
    public void create(LimitPurchaseAddRequest request) {

    }

    @Override
    public void update(LimitPurchaseEditRequest request) {

    }
}
