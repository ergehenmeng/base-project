package com.eghm.handler.process.service;

import com.eghm.dao.model.business.AuditRecord;
import com.eghm.handler.process.BaseAuditHandler;
import com.eghm.model.dto.audit.AuditProcess;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public class DefaultAuditHandler extends BaseAuditHandler {


    @Override
    protected void checkParam(AuditProcess process, AuditRecord record) {

    }

    @Override
    protected void postProcess(AuditProcess process, AuditRecord record) {

    }

    @Override
    protected void finallyProcess(AuditProcess process, AuditRecord record) {

    }
}
