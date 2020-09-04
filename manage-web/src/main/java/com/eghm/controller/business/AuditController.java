package com.eghm.controller.business;

import com.eghm.configuration.security.SecurityOperator;
import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.model.dto.audit.AuditProcess;
import com.eghm.model.dto.audit.AuditProcessRequest;
import com.eghm.model.dto.audit.AuditQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RespBody;
import com.eghm.service.common.AuditService;
import com.eghm.utils.DataUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 殿小二
 * @date 2020/8/26
 */
@Controller
public class AuditController {

    private AuditService auditService;

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    /**
     * 分页查询审批列表
     */
    @PostMapping("/business/audit/list_page")
    public Paging<AuditRecord> listPage(AuditQueryRequest request) {
        request.setOperatorId(SecurityOperatorHolder.getOperatorId());
        PageInfo<AuditRecord> byPage = auditService.getByPage(request);
        return new Paging<>(byPage);
    }


    /**
     * 审批 (所有审批的入口)
     */
    @PostMapping("/business/audit/approval")
    public RespBody<Object> approval(AuditProcessRequest request) {
        AuditProcess process = DataUtil.copy(request, AuditProcess.class);
        SecurityOperator operator = SecurityOperatorHolder.getRequiredOperator();
        process.setAuditOperatorId(operator.getId());
        process.setAuditOperatorName(operator.getOperatorName());
        auditService.audit(process);
        return RespBody.success();
    }

}
