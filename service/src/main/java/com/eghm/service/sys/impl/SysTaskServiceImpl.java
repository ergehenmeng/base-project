package com.eghm.service.sys.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.OnceTask;
import com.eghm.configuration.task.config.TaskRegistrar;
import com.eghm.dto.sys.task.TaskEditRequest;
import com.eghm.dto.sys.task.TaskQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysTaskMapper;
import com.eghm.model.SysTask;
import com.eghm.service.sys.SysTaskService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.operate.task.SysTaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author 二哥很猛
 * @since 2019/9/6 15:19
 */
@Slf4j
@Service("sysTaskService")
@RequiredArgsConstructor
public class SysTaskServiceImpl implements SysTaskService {

    private final SysTaskMapper sysTaskMapper;

    private TaskRegistrar taskRegistrar;

    @Autowired(required = false)
    public void setTaskRegistrar(TaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    @Override
    public Page<SysTaskResponse> getByPage(TaskQueryRequest request) {
        return sysTaskMapper.getByPage(request.createPage(), request);
    }

    @Override
    public void update(TaskEditRequest request) {
        if (!CronExpression.isValidExpression(request.getCronExpression())) {
            throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
        }
        SysTask config = DataUtil.copy(request, SysTask.class);
        sysTaskMapper.updateById(config);
    }

    @Override
    public void execute(Long id, String args) {
        if (taskRegistrar == null) {
            log.error("当前服务尚未激活定时任务, 请使用@EnableTask激活 [{}] [{}]", id, args);
            throw new BusinessException(ErrorCode.TASK_CONFIG_NULL);
        }

        SysTask sysTask = sysTaskMapper.selectById(id);
        if (sysTask == null) {
            log.error("定时任务未查询到[{}]", id);
            throw new BusinessException(ErrorCode.TASK_NULL_ERROR);
        }
        OnceTask onceDetail = new OnceTask();
        onceDetail.setBeanName(sysTask.getBeanName());
        onceDetail.setMethodName(sysTask.getMethodName());
        onceDetail.setArgs(args);
        onceDetail.setExecuteTime(LocalDateTime.now().plus(500, ChronoUnit.MILLIS));
        taskRegistrar.addTask(onceDetail);
    }
}
