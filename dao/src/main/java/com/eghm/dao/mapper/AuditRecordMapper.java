package com.eghm.dao.mapper;

import com.eghm.dao.model.AuditRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 根据角色和状态获取审批列表
     * @param roleList 角色列表
     * @param state 状态
     * @return 审核记录
     */
    List<AuditRecord> getAuditList(@Param("roleList") List<String> roleList, @Param("state") Byte state);
}