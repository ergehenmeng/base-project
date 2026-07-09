package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.sys.log.ManageQueryRequest;
import com.eghm.application.system.query.ManageLogQueryService;
import com.eghm.application.system.service.ManageLogApplicationService;
import com.eghm.domain.system.model.ManageLog;
import com.eghm.domain.system.repository.ManageLogRepository;
import com.eghm.application.shared.vo.operate.log.ManageLogResponse;
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
public class ManageLogApplicationServiceImpl implements ManageLogApplicationService {

    private final ManageLogRepository manageLogRepository;

    private final ManageLogQueryService manageLogQueryService;

    @Override
    public Page<ManageLogResponse> getByPage(ManageQueryRequest request) {
        return manageLogQueryService.getByPage(request.createPage(), request);
    }

    @Override
    public void insertManageLog(ManageLog log) {
        manageLogRepository.save(log);
    }
}
