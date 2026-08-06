package com.eghm.platform.job.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.platform.job.config.task.config.OnceScheduleBean;
import com.eghm.platform.job.config.task.config.TaskRegistrar;
import com.eghm.platform.job.dto.TaskEditRequest;
import com.eghm.platform.job.dto.TaskQueryRequest;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.platform.job.mapper.SysTaskMapper;
import com.eghm.platform.job.entity.SysTask;
import com.eghm.platform.job.service.SysTaskService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.platform.job.vo.SysTaskResponse;
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
@Service
@RequiredArgsConstructor
public class SysTaskServiceImpl implements SysTaskService {

    private TaskRegistrar taskRegistrar;
    
    private final SysTaskMapper sysTaskMapper;

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
        DataUtil.copy(request, SysTask.class, sysTaskMapper::updateById);
    }

    @Override
    public void execute(Long id, String args) {
        if (taskRegistrar == null) {
            log.error("当前服务尚未激活定时任务, 请使用@EnableSchedulingTask激活 [{}] [{}]", id, args);
            throw new BusinessException(ErrorCode.TASK_CONFIG_NULL);
        }
        SysTask sysTask = sysTaskMapper.selectById(id);
        if (sysTask == null) {
            log.error("定时任务未查询到[{}]", id);
            throw new BusinessException(ErrorCode.TASK_NULL_ERROR);
        }
        OnceScheduleBean onceDetail = new OnceScheduleBean();
        onceDetail.setBeanName(sysTask.getBeanName());
        onceDetail.setMethodName(sysTask.getMethodName());
        onceDetail.setArgs(args);
        onceDetail.setExecuteTime(LocalDateTime.now().plus(500, ChronoUnit.MILLIS));
        taskRegistrar.addTask(onceDetail);
    }
}
