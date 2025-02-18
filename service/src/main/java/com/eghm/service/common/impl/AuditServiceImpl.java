package com.eghm.service.common.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.AuditConfig;
import com.eghm.dao.model.AuditRecord;
import com.eghm.dao.model.SysRole;
import com.eghm.handler.process.BaseAuditHandler;
import com.eghm.model.dto.audit.AuditProcess;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.eghm.model.dto.audit.BeginProcess;
import com.eghm.service.common.AuditConfigService;
import com.eghm.service.common.AuditRecordService;
import com.eghm.service.common.AuditService;
import com.eghm.service.common.NumberService;
import com.eghm.service.sys.SysRoleService;
import com.eghm.utils.SpringContextUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@Service("auditService")
@Slf4j
public class AuditServiceImpl implements AuditService {

    private AuditRecordService auditRecordService;

    private AuditConfigService auditConfigService;

    private NumberService numberService;

    private SysRoleService sysRoleService;

    @Autowired
    public void setAuditRecordService(AuditRecordService auditRecordService) {
        this.auditRecordService = auditRecordService;
    }

    @Autowired
    public void setAuditConfigService(AuditConfigService auditConfigService) {
        this.auditConfigService = auditConfigService;
    }

    @Autowired
    public void setNumberService(NumberService numberService) {
        this.numberService = numberService;
    }

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void beginProcess(BeginProcess process) {
        List<AuditConfig> configList = auditConfigService.getConfig(process.getAuditType().name());
        if (CollUtil.isEmpty(configList)) {
            log.warn("流程审核配置信息未查询到:[{}]", process.getAuditType());
            throw new BusinessException(ErrorCode.AUDIT_CONFIG_ERROR);
        }
        AuditConfig config = configList.get(0);
        AuditRecord record = new AuditRecord();
        record.setTitle(process.getTitle());
        record.setApplyId(process.getApplyId());
        record.setApplyOperatorId(process.getApplyOperatorId());
        record.setApplyOperatorName(process.getApplyOperatorName());
        record.setStep(config.getStep());
        record.setRoleType(config.getRoleType());
        record.setAuditType(config.getAuditType());
        record.setState(AuditState.APPLY.getValue());
        record.setAuditNo(numberService.getNumber(process.getAuditType().getPrefix()));
        auditRecordService.insert(record);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void audit(AuditProcess process) {
        BaseAuditHandler handler = SpringContextUtil.getBean(process.getAuditType().getHandler(), BaseAuditHandler.class);
        handler.handler(process);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public PageInfo<AuditRecord> getByPage(AuditQueryRequest request) {
        List<SysRole> userRoleList = sysRoleService.getRoleList(request.getOperatorId());
        List<String> roleList = userRoleList.stream().map(SysRole::getRoleType).collect(Collectors.toList());
        request.setRoleList(roleList);
        return auditRecordService.getByPage(request);
    }

}
