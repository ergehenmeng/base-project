package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.AuditConfig;
import com.eghm.dao.model.BaseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuditConfigMapper extends BaseMapper<AuditConfig> {

    /**
     * 查询指定审核类型的配置列表
     * @param auditType 审核类型
     * @return 列表 正序
     */
    List<AuditConfig> getConfig(@Param("auditType") String auditType);
}