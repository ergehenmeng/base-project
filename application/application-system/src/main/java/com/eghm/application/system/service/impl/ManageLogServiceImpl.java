package com.eghm.application.system.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.dto.sys.log.ManageQueryRequest;
import com.eghm.application.system.service.ManageLogQueryGateway;
import com.eghm.application.system.service.ManageLogService;
import com.eghm.domain.system.model.ManageLog;
import com.eghm.domain.system.repository.ManageLogRepository;
import com.eghm.vo.operate.log.ManageLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日期
 *
 * @author 二哥很猛
 * @since 2019/1/15 17:55
 */
@AllArgsConstructor
@Service("manageLogService")
public class ManageLogServiceImpl implements ManageLogService {

    private final ManageLogRepository manageLogRepository;

    private final ManageLogQueryGateway manageLogQueryGateway;

    @Override
    public Page<ManageLogResponse> getByPage(ManageQueryRequest request) {
        return manageLogQueryGateway.getByPage(request.createPage(), request);
    }

    @Override
    public void insertManageLog(ManageLog log) {
        manageLogRepository.save(log);
    }
}
