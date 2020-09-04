package com.eghm.dao.mapper;

import com.eghm.dao.model.AuditConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuditConfigMapper {


    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    AuditConfig selectByPrimaryKey(Integer id);

    /**
     * 查询指定审核类型的配置列表
     * @param auditType 审核类型
     * @return 列表 正序
     */
    List<AuditConfig> getConfig(@Param("auditType") String auditType);
}