package com.eghm.web.controller;

import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.AuditRecord;
import com.eghm.model.dto.audit.AuditProcess;
import com.eghm.model.dto.audit.AuditProcessRequest;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.eghm.model.dto.ext.Paging;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.common.AuditService;
import com.eghm.utils.DataUtil;
import com.eghm.web.annotation.Mark;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/8/26
 */
@RestController
public class AuditController {

    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * 分页查询审批列表
     */
    @GetMapping("/audit/list_page")
    public Paging<AuditRecord> listPage(AuditQueryRequest request) {
        request.setOperatorId(SecurityOperatorHolder.getOperatorId());
        PageInfo<AuditRecord> byPage = auditService.getByPage(request);
        return new Paging<>(byPage);
    }


    /**
     * 审批 (所有审批的入口)
     */
    @PostMapping("/audit/approval")
    @Mark
    public RespBody<Object> approval(AuditProcessRequest request) {
        AuditProcess process = DataUtil.copy(request, AuditProcess.class);
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        process.setAuditOperatorId(operator.getId());
        process.setAuditOperatorName(operator.getOperatorName());
        auditService.audit(process);
        return RespBody.success();
    }

}
