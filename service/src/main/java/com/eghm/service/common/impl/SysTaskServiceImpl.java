package com.eghm.service.common.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.task.config.OnceTask;
import com.eghm.configuration.task.config.SysTaskRegistrar;
import com.eghm.dto.task.TaskEditRequest;
import com.eghm.dto.task.TaskQueryRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.SysTaskMapper;
import com.eghm.model.SysTask;
import com.eghm.service.common.SysTaskService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.task.SysTaskResponse;
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

    private SysTaskRegistrar sysTaskRegistrar;

    @Autowired(required = false)
    public void setSysTaskRegistrar(SysTaskRegistrar sysTaskRegistrar) {
        this.sysTaskRegistrar = sysTaskRegistrar;
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
        if (sysTaskRegistrar == null) {
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
        sysTaskRegistrar.addTask(onceDetail);
    }
}
