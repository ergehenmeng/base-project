package com.eghm.process.handler;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.AuditState;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.dao.model.business.AuditConfig;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.process.dto.AuditProcess;
import com.eghm.process.service.AuditConfigService;
import com.eghm.process.service.AuditRecordService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/25
 */
@Slf4j
public class AuditHandler {

    private AuditRecordService auditRecordService;

    private AuditConfigService auditConfigService;

    @Autowired
    public void setAuditRecordService(AuditRecordService auditRecordService) {
        this.auditRecordService = auditRecordService;
    }

    @Autowired
    public void setAuditConfigService(AuditConfigService auditConfigService) {
        this.auditConfigService = auditConfigService;
    }

    /**
     * 审批
     * @param process 审批信息
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void handler(AuditProcess process) {
        AuditRecord record = this.getCheckedRecord(process.getId());
        this.checkParam(process, record);
        record.setState(process.getState().getValue());
        record.setAuditTime(DateUtil.getNow());
        record.setAuditOperatorId(process.getAuditOperatorId());
        record.setAuditOperatorName(process.getAuditOperatorName());
        record.setOpinion(process.getOpinion());
        auditRecordService.updateSelective(record);

        this.postProcess(process, record);

        if (process.getState() == AuditState.PASS) {
            List<AuditConfig> configList = this.getCheckedConfig(process.getAuditType().getValue());
            AuditConfig nextConfig = this.getNextStepConfig(record.getStep(), configList);
            if (nextConfig != null) {
                AuditRecord nextAuditRecord = this.generateNextAuditRecord(record, nextConfig);
                this.nextProcess(process, record, nextAuditRecord);
                return;
            }
        }
        this.finallyProcess(process, record);
    }

    /**
     * 生成下一个审批节点信息
     * @param currentRecord 当前审批
     * @param nextConfig 下个节点审批信息
     * @return nextRecord
     */
    private AuditRecord generateNextAuditRecord(AuditRecord currentRecord, AuditConfig nextConfig) {
        AuditRecord nextRecord = DataUtil.copy(currentRecord, AuditRecord.class, "id", "opinion", "auditOperatorId", "auditOperatorName", "auditTime", "addTime", "updateTime");
        nextRecord.setState(AuditState.APPLY.getValue());
        nextRecord.setStep(nextConfig.getStep());
        nextRecord.setRoleType(nextConfig.getRoleType());
        nextRecord.setAuditType(nextConfig.getAuditType());
        auditRecordService.insertSelective(nextRecord);
        return nextRecord;
    }

    /**
     * 获取下一个节点的审核配置信息
     * @param currentStep 当前节点
     * @param configList 节点配置信息
     * @return config
     */
    private AuditConfig getNextStepConfig(int currentStep, List<AuditConfig> configList) {
        return configList.stream().filter(config -> config.getStep() > currentStep).findFirst().orElse(null);
    }


    /**
     * 当前审核流程结束
     */
    public void postProcess(AuditProcess process, AuditRecord record) {
    }

    /**
     * 整个审核流程审核结束
     */
    public void finallyProcess(AuditProcess process, AuditRecord record) {
    }

    /**
     * 下一个处理节点
     */
    public void nextProcess(AuditProcess process, AuditRecord record, AuditRecord nextRecord) {

    }


    /**
     * 校验其他参数信息
     * @param process 前台传递过来的审批信息
     * @param record 审批申请信息
     */
    public void checkParam(AuditProcess process, AuditRecord record) {
    }

    /**
     * 获取审批信息
     * @param id id
     * @return 已经校验过的审批信息
     */
    private AuditRecord getCheckedRecord(Integer id) {
        AuditRecord record = auditRecordService.getById(id);
        if (record == null) {
            log.warn("申请信息未查询到 id:[{}]", id);
            throw new BusinessException(ErrorCode.AUDIT_APPLY_NULL);
        }
        if (record.getState() != AuditState.APPLY.getValue()) {
            log.warn("申请信息未查询到 id:[{}], state:[{}]", id, record.getState());
            throw new BusinessException(ErrorCode.AUDIT_REDO);
        }
        return record;
    }

    /**
     * 获取流程配置信息
     * @param auditType 审核类型
     * @return 配置步骤信息
     */
    private List<AuditConfig> getCheckedConfig(String auditType) {
        List<AuditConfig> configList = auditConfigService.getConfig(auditType);
        if (CollUtil.isEmpty(configList)) {
            log.warn("流程审核配置信息未查询到:[{}]", auditType);
            throw new BusinessException(ErrorCode.AUDIT_CONFIG_ERROR);
        }
        return configList;
    }

}
