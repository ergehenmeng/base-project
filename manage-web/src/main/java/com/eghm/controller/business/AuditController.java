package com.eghm.controller.business;

import com.eghm.configuration.security.SecurityOperatorHolder;
import com.eghm.dao.model.business.AuditRecord;
import com.eghm.model.dto.business.audit.AuditQueryRequest;
import com.eghm.model.ext.Paging;
import com.eghm.model.ext.RequestThreadLocal;
import com.eghm.process.service.AuditService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
    @GetMapping("/business/audit/list_page")
    public Paging<AuditRecord> listPage(AuditQueryRequest request) {
        request.setOperatorId(SecurityOperatorHolder.getOperatorId());
        PageInfo<AuditRecord> byPage = auditService.getByPage(request);
        return new Paging<>(byPage);
    }

}
