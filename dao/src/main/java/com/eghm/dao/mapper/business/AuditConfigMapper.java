package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.AuditConfig;

public interface AuditConfigMapper {


    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    AuditConfig selectByPrimaryKey(Integer id);

}