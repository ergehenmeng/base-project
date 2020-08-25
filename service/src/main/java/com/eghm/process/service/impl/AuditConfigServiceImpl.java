package com.eghm.process.service.impl;

import com.eghm.common.constant.CacheConstant;
import com.eghm.common.enums.AuditType;
import com.eghm.dao.mapper.business.AuditConfigMapper;
import com.eghm.dao.model.business.AuditConfig;
import com.eghm.process.service.AuditConfigService;
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
    @Cacheable(cacheNames = CacheConstant.AUDIT_CONFIG, key = "#type.value", cacheManager = "shortCacheManager")
    public List<AuditConfig> getConfig(AuditType type) {
        return auditConfigMapper.getConfig(type.getValue());
    }
}
