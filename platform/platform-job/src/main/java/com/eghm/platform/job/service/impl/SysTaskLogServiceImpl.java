package com.eghm.platform.job.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.job.dto.TaskLogQueryRequest;
import com.eghm.platform.job.mapper.SysTaskLogMapper;
import com.eghm.platform.job.entity.SysTaskLog;
import com.eghm.platform.job.service.SysTaskLogService;
import com.eghm.platform.job.vo.SysTaskLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
@AllArgsConstructor
@Service("sysTaskLogService")
public class SysTaskLogServiceImpl implements SysTaskLogService {

    private final SysTaskLogMapper sysTaskLogMapper;

    @Override
    public Page<SysTaskLogResponse> getByPage(TaskLogQueryRequest request) {
        return sysTaskLogMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void addTaskLog(SysTaskLog log) {
        sysTaskLogMapper.insert(log);
    }

    @Override
    public String getErrorMsg(Long id) {
        return sysTaskLogMapper.getErrorMsg(id);
    }
}
