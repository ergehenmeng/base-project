package com.eghm.configuration.task.config;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.eghm.common.utils.DateUtil;
import com.eghm.common.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.dao.model.business.TaskConfig;
import com.eghm.service.common.TaskConfigService;
import com.eghm.service.system.impl.SystemConfigApi;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 二哥很猛
 */
@Slf4j
public class SystemTaskRegistrar implements DisposableBean {

    @Autowired
    private TaskConfigService taskConfigService;

    @Autowired
    private SystemConfigApi systemConfigApi;

    @Autowired
    private TaskScheduler taskScheduler;

    /**
     * 周期定时任务
     */
    private final Map<String, CronTriggerTask> cronTaskMap = new ConcurrentHashMap<>(32);

    /**
     * 任务执行句柄
     */
    private final Map<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>(32);

    /**
     * 单次定时任务
     */
    private final Map<String, OnceTriggerTask> onceTaskMap = new ConcurrentHashMap<>(32);

    /**
     * 计数器 用于单次任务的nid生成
     */
    private AtomicLong counter = new AtomicLong();

    /**
     * 加载或刷新系统中配置的定时任务
     */
    public synchronized void loadOrRefreshTask() {
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
        //cron校验
        this.verifyCronExpression(taskList);
        //移除不需要运行的任务
        this.removeCronTask(taskList);
        //添加新的任务
        this.addCronTask(taskList);
    }

    /**
     * 添加cron定时任务
     *
     * @param taskList 待添加的定时任务列表
     */
    private void addCronTask(List<CronTriggerTask> taskList) {
        for (CronTriggerTask task : taskList) {
            this.addCronTask(task);
        }
    }

    /**
     * 添加cron定时任务
     *
     * @param task 待添加的定时任务
     */
    private void addCronTask(CronTriggerTask task) {
        if (cronTaskMap.containsKey(task.getNid()) && cronTaskMap.get(task.getNid()).getCronExpression().equals(task.getCronExpression())) {
            log.info("定时任务配置信息未发生变化 nid:[{}]", task.getNid());
            return;
        }
        if (scheduledFutures.containsKey(task.getNid())) {
            //定时任务存在,但配置发生变化 移除旧定时任务
            scheduledFutures.get(task.getNid()).cancel(false);
        }
        ScheduledFuture<?> schedule = taskScheduler.schedule(task.getRunnable(), task.getTrigger());
        scheduledFutures.put(task.getNid(), schedule);
        cronTaskMap.put(task.getNid(), task);
    }

    /**
     * 移除旧的定时任务,注意:
     * 1.如果旧定时任务与新的要执行的定时任务一样,则不移除.在添加定时任务时再判断(减少过多的停止任务的操作)
     * 2.如果旧定时任务是仅执行一次的定时任务,则不移除.由系统参数 task_max_survival_time 来决定是否移除
     *
     * @param taskList 指定的任务列表
     * @see SystemTaskRegistrar#addTask(OnceTask)
     */
    private void removeCronTask(List<CronTriggerTask> taskList) {
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
    }

    /**
     * 校验任务的的cron表达式是否正确
     *
     * @param taskList cron任务列表
     */
    private void verifyCronExpression(List<CronTriggerTask> taskList) {
        for (CronTriggerTask task : taskList) {
            if (StringUtil.isBlank(task.getCronExpression()) || !CronSequenceGenerator.isValidExpression(task.getCronExpression())) {
                log.error("定时任务表达式配置错误 nid:[{}],cron:[{}]", task.getNid(), task.getCronExpression());
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
        this.cancelTask();
        task.setNid(task.getNid() + "-" + counter.getAndIncrement());
        OnceTriggerTask onceTriggerTask = new OnceTriggerTask(task);
        ScheduledFuture<?> schedule = taskScheduler.schedule(onceTriggerTask.getRunnable(), onceTriggerTask.getTrigger());
        scheduledFutures.put(task.getNid(), schedule);
        onceTaskMap.put(task.getNid(), onceTriggerTask);
    }

    /**
     * 移除过期的一次性任务
     */
    private void cancelTask() {
        Date now = DateUtil.getNow();
        int maxSurvivalTime = systemConfigApi.getInt(ConfigConstant.TASK_MAX_SURVIVAL_TIME);
        Iterator<Map.Entry<String, OnceTriggerTask>> iterator = onceTaskMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, OnceTriggerTask> next = iterator.next();
            if (next.getValue().shouldRemove(now, maxSurvivalTime)) {
                log.info("移除定时任务 nid:[{}]", next.getKey());
                iterator.remove();
                ScheduledFuture<?> future = scheduledFutures.remove(next.getKey());
                if (future != null) {
                    future.cancel(false);
                }
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        for (ScheduledFuture<?> future : scheduledFutures.values()) {
            if (future != null) {
                future.cancel(true);
            }
        }
    }
}