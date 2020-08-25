package com.eghm.process.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.dao.model.business.AuditConfig;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.process.dto.BeginProcess;
import com.eghm.process.service.AuditConfigService;
import com.eghm.process.service.AuditRecordService;
import com.eghm.process.service.AuditService;
import com.eghm.service.common.NumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public void beginProcess(BeginProcess process) {
        List<AuditConfig> configList = auditConfigService.getConfig(process.getAuditType().getValue());
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
        auditRecordService.insertSelective(record);
    }

    @Override
    public void audit() {

    }
}
