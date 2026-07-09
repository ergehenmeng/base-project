package com.eghm.infrastructure.shared.configuration.task.config;

import com.eghm.domain.shared.service.AlarmService;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.application.shared.lock.RedisLock;
import com.eghm.infrastructure.persistence.mybatis.mapper.SysTaskMapper;
import com.eghm.infrastructure.persistence.mybatis.po.SysTaskPO;
import com.eghm.application.system.service.SysTaskLogApplicationService;
import com.eghm.application.shared.utils.LoggerUtil;
import com.eghm.infrastructure.persistence.mybatis.util.MybatisUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronExpression;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.eghm.application.shared.utils.StringUtil.isBlank;

/**
 * @author 二哥很猛
 */
@Slf4j
@AllArgsConstructor
public class TaskRegistrar {
    
    private final RedisLock redisLock;
    
    private final AlarmService alarmService;
    
    private final SysTaskMapper sysTaskMapper;

    private final TaskScheduler taskScheduler;

    private final SysTaskLogApplicationService sysTaskLogService;

    /**
     * 任务执行句柄
     */
    private final Map<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>(32);

    /**
     * 计数器 用于单次任务的nid生成
     */
    private final AtomicLong counter = new AtomicLong();

    /**
     * 加载或刷新系统中配置的定时任务
     */
    @PostConstruct
    public synchronized void reloadTask() {
        List<SysTaskPO> taskConfigList = MybatisUtil.getList(sysTaskMapper, SysTaskPO::getState, true);
        List<CronTaskWrapper> taskList = new ArrayList<>();
        for (SysTaskPO task : taskConfigList) {
            CronTaskWrapper triggerTask = new CronTaskWrapper(task, redisLock, alarmService, sysTaskLogService);
            taskList.add(triggerTask);
        }
        this.doRefreshTask(taskList);
        LoggerUtil.print("定时任务配置信息加载完成");
    }

    /**
     * 重置定时任务
     *
     * @param taskList 新的定时任务配置列表
     */
    private void doRefreshTask(List<CronTaskWrapper> taskList) {
        // cron校验
        this.verifyCronExpression(taskList);
        // 移除不需要运行的任务
        this.removeCronTask(taskList);
        // 添加新的任务
        this.addCronTask(taskList);
    }

    /**
     * 添加cron定时任务
     *
     * @param taskList 待添加的定时任务列表
     */
    private void addCronTask(List<CronTaskWrapper> taskList) {
        for (CronTaskWrapper task : taskList) {
            this.addCronTask(task);
        }
    }

    /**
     * 添加cron定时任务
     *
     * @param task 待添加的定时任务
     */
    private void addCronTask(CronTaskWrapper task) {
        if (scheduledFutures.containsKey(task.getNid())) {
            // 定时任务存在,但配置发生变化 移除旧定时任务
            scheduledFutures.get(task.getNid()).cancel(false);
        }
        ScheduledFuture<?> schedule = taskScheduler.schedule(task.getRunnable(), task.getTrigger());
        scheduledFutures.put(task.getNid(), schedule);
    }

    /**
     * 移除旧的定时任务,注意:
     * 1.如果旧定时任务与新的要执行的定时任务一样,则不移除.在添加定时任务时再判断(减少过多的停止任务的操作)
     *
     * @param taskList 指定的任务列表
     * @see TaskRegistrar#addTask(OnceScheduleBean)
     */
    private void removeCronTask(List<CronTaskWrapper> taskList) {
        boolean isEmpty = taskList.isEmpty();
        Iterator<Map.Entry<String, ScheduledFuture<?>>> iterator = scheduledFutures.entrySet().iterator();
        Set<String> newTaskIds = taskList.stream().map(CronTaskWrapper::getNid).collect(Collectors.toSet());
        while (iterator.hasNext()) {
            Map.Entry<String, ScheduledFuture<?>> entry = iterator.next();
            // 将所有不在指定任务列表的中已经在运行的任务全部取消
            boolean shouldCancel = (isEmpty || !newTaskIds.contains(entry.getKey()));
            if (shouldCancel) {
                entry.getValue().cancel(false);
                iterator.remove();
            }
        }
    }

    /**
     * 校验任务的的cron表达式是否正确
     *
     * @param taskList cron任务列表
     */
    private void verifyCronExpression(List<CronTaskWrapper> taskList) {
        for (CronTaskWrapper task : taskList) {
            if (isBlank(task.getExpression()) || !CronExpression.isValidExpression(task.getExpression())) {
                log.error("定时任务表达式配置错误 nid:[{}],cron:[{}]", task.getNid(), task.getExpression());
                throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
            }
        }
    }

    /**
     * 添加任务,只能添加仅执行一次的定时任务
     *
     * @param task 任务配置信息
     */
    public void addTask(OnceScheduleBean task) {
        String nid = task.getBeanName() + "-" + task.getMethodName() + "-" + counter.getAndIncrement();
        Invoker invoker = new Invoker(task, redisLock, alarmService, sysTaskLogService);
        ScheduledFuture<?> schedule = taskScheduler.schedule(invoker, task.getExecuteTime().atZone(ZoneId.systemDefault()).toInstant());
        scheduledFutures.put(nid, schedule);
    }

    @PreDestroy
    public void destroy() {
        for (ScheduledFuture<?> future : scheduledFutures.values()) {
            if (future != null) {
                future.cancel(true);
            }
        }
    }
}
