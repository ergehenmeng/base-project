package com.eghm.platform.audit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.audit.dto.ManageQueryRequest;
import com.eghm.platform.audit.mapper.ManageLogMapper;
import com.eghm.platform.audit.entity.ManageLog;
import com.eghm.platform.audit.service.ManageLogService;
import com.eghm.platform.audit.vo.ManageLogResponse;
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

    private final ManageLogMapper manageLogMapper;

    @Override
    public Page<ManageLogResponse> getByPage(ManageQueryRequest request) {
        return manageLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void insertManageLog(ManageLog log) {
        manageLogMapper.insert(log);
    }

}
