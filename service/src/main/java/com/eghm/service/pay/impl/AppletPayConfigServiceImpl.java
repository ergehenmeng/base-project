package com.eghm.service.pay.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.service.pay.enums.MerchantType;
import com.eghm.dao.mapper.AppletPayConfigMapper;
import com.eghm.dao.model.AppletPayConfig;
import com.eghm.service.pay.AppletPayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("appletPayConfigService")
public class AppletPayConfigServiceImpl implements AppletPayConfigService {

    private AppletPayConfigMapper appletPayConfigMapper;

    @Autowired
    public void setAppletPayConfigMapper(AppletPayConfigMapper appletPayConfigMapper) {
        this.appletPayConfigMapper = appletPayConfigMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.APPLET_PAY_CONFIG, cacheManager = "longCacheManager", key = "#type.code")
    public AppletPayConfig getByNid(MerchantType type) {
        return appletPayConfigMapper.getByNid(type.getCode());
    }
}