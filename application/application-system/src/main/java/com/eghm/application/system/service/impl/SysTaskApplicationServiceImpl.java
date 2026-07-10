package com.eghm.application.system.service.impl;

import com.eghm.application.shared.dto.sys.task.TaskEditRequest;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.system.schedule.SysTaskScheduleService;
import com.eghm.application.system.service.SysTaskApplicationService;
import com.eghm.domain.system.model.SysTask;
import com.eghm.domain.system.repository.SysTaskRepository;
import com.eghm.application.shared.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author 二哥很猛
 * @since 2019/9/6 15:19
 */
@Slf4j
@RequiredArgsConstructor
@Service("sysTaskService")
public class SysTaskApplicationServiceImpl implements SysTaskApplicationService {

    private final SysTaskRepository sysTaskRepository;

    private final SysTaskScheduleService sysTaskScheduleService;

    @Override
    public void update(TaskEditRequest request) {
        if (!CronExpression.isValidExpression(request.getCronExpression())) {
            throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
        }
        sysTaskRepository.update(DataUtil.copy(request, SysTask.class));
    }

    @Override
    public void refresh() {
        if (!sysTaskScheduleService.reloadTask()) {
            throw new BusinessException(ErrorCode.TASK_CONFIG_NULL);
        }
    }

    @Override
    public void execute(Long id, String args) {
        SysTask task = sysTaskRepository.findById(id);
        if (task == null) {
            log.error("定时任务未查询到[{}]", id);
            throw new BusinessException(ErrorCode.TASK_NULL_ERROR);
        }
        boolean scheduled = sysTaskScheduleService.scheduleOnce(task.getBeanName(), task.getMethodName(), args, LocalDateTime.now().plus(500, ChronoUnit.MILLIS));
        if (!scheduled) {
            log.error("当前服务尚未激活定时任务, 请使用@EnableSchedulingTask激活 [{}] [{}]", id, args);
            throw new BusinessException(ErrorCode.TASK_CONFIG_NULL);
        }
    }
}
