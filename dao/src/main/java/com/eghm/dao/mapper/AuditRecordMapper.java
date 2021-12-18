package com.eghm.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eghm.dao.model.AuditRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuditRecordMapper extends BaseMapper<AuditRecord> {

    /**
     * 根据角色和状态获取审批列表
     * @param roleList 角色列表
     * @param state 状态
     * @return 审核记录
     */
    List<AuditRecord> getAuditList(@Param("roleList") List<String> roleList, @Param("state") Byte state);
}