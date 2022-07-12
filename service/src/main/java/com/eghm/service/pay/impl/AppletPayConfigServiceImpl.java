package com.eghm.service.pay.impl;

import com.eghm.dao.model.AppletPayConfig;
import com.eghm.service.pay.AppletPayConfigService;
import com.eghm.service.pay.enums.MerchantType;
import com.eghm.service.cache.CacheProxyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("appletPayConfigService")
@AllArgsConstructor
public class AppletPayConfigServiceImpl implements AppletPayConfigService {

    private final CacheProxyService cacheProxyService;

    @Override
    public AppletPayConfig getByNid(MerchantType type) {
        return cacheProxyService.getPayByNid(type);
    }
}