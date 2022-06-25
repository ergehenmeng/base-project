package com.eghm.service.pay.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.AppletPayConfigMapper;
import com.eghm.dao.model.AppletPayConfig;
import com.eghm.service.pay.AppletPayConfigService;
import com.eghm.service.pay.enums.MerchantType;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 */
@Service("appletPayConfigService")
@AllArgsConstructor
public class AppletPayConfigServiceImpl implements AppletPayConfigService {

    private final AppletPayConfigMapper appletPayConfigMapper;

    @Override
    @Cacheable(cacheNames = CacheConstant.APPLET_PAY_CONFIG, cacheManager = "longCacheManager", key = "#type.code")
    public AppletPayConfig getByNid(MerchantType type) {
        LambdaQueryWrapper<AppletPayConfig> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(AppletPayConfig::getNid, type.getCode());
        wrapper.last(" limit 1 ");
        return appletPayConfigMapper.selectOne(wrapper);
    }
}