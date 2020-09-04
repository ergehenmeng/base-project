package com.eghm.service.common.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.dao.mapper.AuditConfigMapper;
import com.eghm.dao.model.AuditConfig;
import com.eghm.service.common.AuditConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@Service("auditConfigService")
public class AuditConfigServiceImpl implements AuditConfigService {

    private AuditConfigMapper auditConfigMapper;

    @Autowired
    public void setAuditConfigMapper(AuditConfigMapper auditConfigMapper) {
        this.auditConfigMapper = auditConfigMapper;
    }

    @Override
    @Cacheable(cacheNames = CacheConstant.AUDIT_CONFIG, key = "#p0", cacheManager = "shortCacheManager")
    public List<AuditConfig> getConfig(String auditType) {
        return auditConfigMapper.getConfig(auditType);
    }
}
