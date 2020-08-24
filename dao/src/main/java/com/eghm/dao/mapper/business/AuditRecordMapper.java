package com.eghm.dao.mapper.business;

import com.eghm.dao.model.business.AuditRecord;

public interface AuditRecordMapper {


    /**
     * 插入不为空的记录
     *
     * @param record 条件 
     */
    int insertSelective(AuditRecord record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id 条件 
     */
    AuditRecord selectByPrimaryKey(Integer id);

    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record 条件 
     */
    int updateByPrimaryKeySelective(AuditRecord record);

}