package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.sys.task.TaskLogQueryRequest;
import com.eghm.mapper.SysTaskLogMapper;
import com.eghm.model.SysTaskLog;
import com.eghm.service.sys.SysTaskLogService;
import com.eghm.vo.log.SysTaskLogResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @since 2019/9/11 11:18
 */
@Service("sysTaskLogService")
@AllArgsConstructor
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
