package com.eghm.service.common;

import com.eghm.dao.model.AuditConfig;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
public interface AuditConfigService {

    /**
     * 根据审核类型获取审核配置列表,由小到大排序
     * @param auditType 审核类型
     * @return 审核配置信息
     */
    List<AuditConfig> getConfig(String auditType);
}
