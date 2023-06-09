package com.eghm.configuration.task.config;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.eghm.model.SysTask;
import com.eghm.service.common.SysTaskService;
import com.eghm.utils.DataUtil;
import com.eghm.utils.LoggerUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronExpression;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 二哥很猛
 */
@Slf4j
@AllArgsConstructor
public class SysTaskRegistrar {

    private final SysTaskService taskConfigService;

    private final TaskScheduler taskScheduler;

    /**
     * 周期定时任务
     */
    private final Map<String, SysCronTask> cronTaskMap = new ConcurrentHashMap<>(32);

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
    public synchronized void loadOrRefreshTask() {
        List<SysTask> taskConfigs = taskConfigService.getAvailableList();
        List<SysCronTask> taskList = new ArrayList<>();
        for (SysTask taskConfig : taskConfigs) {
            SysCronTask triggerTask = new SysCronTask(DataUtil.copy(taskConfig, CronTask.class));
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
    private void doRefreshTask(List<SysCronTask> taskList) {
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
    private void addCronTask(List<SysCronTask> taskList) {
        for (SysCronTask task : taskList) {
            this.addCronTask(task);
        }
    }

    /**
     * 添加cron定时任务
     *
     * @param task 待添加的定时任务
     */
    private void addCronTask(SysCronTask task) {
        if (cronTaskMap.containsKey(task.getNid()) && cronTaskMap.get(task.getNid()).getExpression().equals(task.getExpression())) {
            log.info("定时任务配置信息未发生变化 nid:[{}]", task.getNid());
            return;
        }
        if (scheduledFutures.containsKey(task.getNid())) {
            // 定时任务存在,但配置发生变化 移除旧定时任务
            scheduledFutures.get(task.getNid()).cancel(false);
        }
        ScheduledFuture<?> schedule = taskScheduler.schedule(task.getRunnable(), task.getTrigger());
        scheduledFutures.put(task.getNid(), schedule);
        cronTaskMap.put(task.getNid(), task);
    }

    /**
     * 移除旧的定时任务,注意:
     * 1.如果旧定时任务与新的要执行的定时任务一样,则不移除.在添加定时任务时再判断(减少过多的停止任务的操作)
     *
     * @param taskList 指定的任务列表
     * @see SysTaskRegistrar#addTask(OnceTask)
     */
    private void removeCronTask(List<SysCronTask> taskList) {
        boolean isEmpty = taskList.isEmpty();
        Iterator<Map.Entry<String, ScheduledFuture<?>>> iterator = scheduledFutures.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ScheduledFuture<?>> entry = iterator.next();
            // 将所有不在指定任务列表的中已经在运行的任务全部取消
            boolean shouldCancel = (isEmpty || taskList.stream().map(SysCronTask::getNid).noneMatch(s -> s.equals(entry.getKey())));
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
    private void verifyCronExpression(List<SysCronTask> taskList) {
        for (SysCronTask task : taskList) {
            if (StrUtil.isBlank(task.getExpression()) || !CronExpression.isValidExpression(task.getExpression())) {
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
    public void addTask(OnceTask task) {
        String nid = task.getBeanName() + "-" + task.getMethodName() + "-" + counter.getAndIncrement();
        ScheduledFuture<?> schedule = taskScheduler.schedule(new RunnableTask(task), task.getExecuteTime());
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
