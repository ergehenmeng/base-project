package com.eghm.configuration.job;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.common.utils.StringUtil;
import com.eghm.dao.model.business.TaskConfig;
import com.eghm.service.common.TaskConfigService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:49
 */
@Configuration
@ConditionalOnProperty(prefix = "application", name = "job")
@Slf4j
@EnableScheduling
public class TaskConfiguration implements SchedulingConfigurer, DisposableBean {

    @Autowired
    private TaskConfigService taskConfigService;

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private final Map<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    /**
     * 周期定时任务
     */
    private final Map<String, CronTriggerTask> cronTaskMap = new ConcurrentHashMap<>();

    /**
     * 单次定时任务
     */
    private final Map<String, OnceTriggerTask> onceTaskMap = new ConcurrentHashMap<>();

    /**
     * 计数器 用于单次任务的nid生成
     */
    private AtomicLong counter = new AtomicLong();

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar taskRegistrar) {
        this.scheduledTaskRegistrar = taskRegistrar;
        taskRegistrar.setScheduler(this.createScheduler());
        this.openOrRefreshTask();
    }

    /**
     * 创建定时任务线程池
     */
    private TaskScheduler createScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("定时任务-");
        scheduler.afterPropertiesSet();
        return scheduler;
    }

    /**
     * 开启或刷新定时任务
     */
    public synchronized void openOrRefreshTask() {
        log.info("定时任务配置信息开始加载...");
        List<TaskConfig> taskConfigs = taskConfigService.getAvailableList();
        List<CronTriggerTask> taskList = new ArrayList<>();
        for (TaskConfig taskConfig : taskConfigs) {
            CronTriggerTask triggerTask = new CronTriggerTask(DataUtil.copy(taskConfig, CronTask.class));
            taskList.add(triggerTask);
        }
        this.doRefreshTask(taskList);
        log.info("定时任务配置信息加载完成..");
    }

    /**
     * 重置定时任务
     *
     * @param taskList 新的定时任务配置列表
     */
    private void doRefreshTask(List<CronTriggerTask> taskList) {
        for (CronTriggerTask task : taskList) {
            if (StringUtil.isBlank(task.getCronExpression()) || !CronSequenceGenerator.isValidExpression(task.getCronExpression())) {
                log.error("定时任务表达式配置错误 nid:[{}],cron:[{}]", task.getNid(), task.getCronExpression());
                throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
            }
        }

        boolean isEmpty = taskList.isEmpty();
        Iterator<Map.Entry<String, ScheduledFuture<?>>> iterator = scheduledFutures.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ScheduledFuture<?>> entry = iterator.next();
            //将所有不在指定任务列表的中已经在运行的任务全部取消,注意该移除不包含一次执行的定时任务
            boolean shouldCancel = (isEmpty || taskList.stream().map(CronTriggerTask::getNid).noneMatch(s -> s.equals(entry.getKey()))) && !onceTaskMap.containsKey(entry.getKey());
            if (shouldCancel) {
                entry.getValue().cancel(false);
                iterator.remove();
            }
        }
        for (CronTriggerTask task : taskList) {
            if (cronTaskMap.containsKey(task.getNid()) && cronTaskMap.get(task.getNid()).getCronExpression().equals(task.getCronExpression())) {
                log.info("定时任务配置信息未发生变化 nid:[{}]", task.getNid());
                continue;
            }
            if (scheduledFutures.containsKey(task.getNid())) {
                //定时任务存在,但配置发生变化 移除旧定时任务
                scheduledFutures.get(task.getNid()).cancel(false);
            }
            TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
            if (scheduler != null) {
                ScheduledFuture<?> schedule = scheduler.schedule(task.getRunnable(), task.getTrigger());
                scheduledFutures.put(task.getNid(), schedule);
            }
            cronTaskMap.put(task.getNid(), task);
        }
    }

    @Override
    public void destroy() {
        //默认情况下 取消操作ScheduledTaskRegistrar#destroy,由于手动调用scheduler#schedule,因此不受ScheduledTaskRegistrar管理
        for (ScheduledFuture<?> future : scheduledFutures.values()) {
            if (future != null) {
                future.cancel(true);
            }
        }
    }

    /**
     * 添加任务,只能添加仅执行一次的定时任务
     * @param task 任务配置信息
     */
    public void addTask(OnceTask task) {
        this.cancelTask();
        task.setNid(task.getNid() + "-" + counter.getAndDecrement());
        TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
        OnceTriggerTask onceTriggerTask = new OnceTriggerTask(task);
        if (scheduler != null) {
            ScheduledFuture<?> schedule = scheduler.schedule(onceTriggerTask.getRunnable(), onceTriggerTask.getTrigger());
            scheduledFutures.put(task.getNid(), schedule);
        }
        onceTaskMap.put(task.getNid(), onceTriggerTask);
    }

    /**
     * 移除过期的一次性任务
     */
    private void cancelTask() {
        Date now = DateUtil.getNow();
        for (Map.Entry<String, OnceTriggerTask> entry : onceTaskMap.entrySet()) {
            if(entry.getValue().shouldRemove(now)){
                log.info("移除定时任务 nid:[{}]",entry.getKey());
                ScheduledFuture<?> future = scheduledFutures.remove(entry.getKey());
                if (future != null){
                    future.cancel(false);
                }
            }
        }
    }
}
