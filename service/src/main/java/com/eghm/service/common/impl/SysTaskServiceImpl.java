package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
import com.eghm.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:19
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
    public Page<SysTask> getByPage(TaskQueryRequest request) {
        LambdaQueryWrapper<SysTask> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(request.getState() != null, SysTask::getState, request.getState());
        wrapper.and(StrUtil.isNotBlank(request.getQueryName()), queryWrapper ->
                queryWrapper.like(SysTask::getTitle, request.getQueryName()).or()
                        .like(SysTask::getMethodName, request.getQueryName()).or()
                        .like(SysTask::getBeanName, request.getQueryName()));
        return sysTaskMapper.selectPage(request.createPage(), wrapper);
    }

    @Override
    public SysTask getById(Long id) {
        return sysTaskMapper.selectById(id);
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
            log.error("该服务尚未激活定时任务, 请使用@EnableTask激活 [{}] [{}]", id, args);
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
        onceDetail.setExecuteTime(DateUtil.addSeconds(DateUtil.getNow(), 1));
        sysTaskRegistrar.addTask(onceDetail);
    }
}
