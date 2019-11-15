package com.fanyin.configuration.job;

import com.fanyin.common.enums.ErrorCode;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.common.utils.StringUtil;
import com.fanyin.dao.model.business.TaskConfig;
import com.fanyin.service.common.TaskConfigService;
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
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author 二哥很猛
 * @date 2019/9/6 14:49
 */
@Configuration
@ConditionalOnProperty(prefix = "application",name = "job")
@Slf4j
@EnableScheduling
public class DynamicTask implements SchedulingConfigurer, DisposableBean {

    @Autowired
    private TaskConfigService taskConfigService;

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    private final Map<String, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();

    private final Map<String, DynamicTriggerTask> triggerTaskMap = new ConcurrentHashMap<>();


    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar taskRegistrar) {
        this.scheduledTaskRegistrar = taskRegistrar;
        taskRegistrar.setScheduler(this.createScheduler());
        this.openOrRefreshTask();
    }

    /**
     * 创建定时任务线程池
     */
    private TaskScheduler createScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("定时任务-");
        scheduler.afterPropertiesSet();
        return scheduler;
    }

    /**
     * 开启或刷新定时任务
     */
    public synchronized void openOrRefreshTask(){
        log.info("定时任务配置信息开始加载...");
        List<TaskConfig> taskConfigs = taskConfigService.getAvailableList();
        if(!CollectionUtils.isEmpty(taskConfigs)){
            List<DynamicTriggerTask> taskList = new ArrayList<>();
            for (TaskConfig taskConfig : taskConfigs) {
                DynamicTriggerTask triggerTask = new DynamicTriggerTask(taskConfig.getNid(), taskConfig.getBeanName(), taskConfig.getCronExpression());
                taskList.add(triggerTask);
            }
            this.doRefreshTask(taskList);
        }
        log.info("定时任务配置信息加载完成..");
    }

    /**
     * 重置定时任务
     * @param taskList 新的定时任务配置列表
     */
    private void doRefreshTask(List<DynamicTriggerTask> taskList){
        for (DynamicTriggerTask task : taskList) {
            if(StringUtil.isBlank(task.getCronExpression()) || !CronSequenceGenerator.isValidExpression(task.getCronExpression())){
                log.error("定时任务表达式配置错误 nid:[{}],cron:[{}]",task.getNid(),task.getCronExpression());
                throw new BusinessException(ErrorCode.CRON_CONFIG_ERROR);
            }
        }
        for (Map.Entry<String, ScheduledFuture<?>> entry : scheduledFutures.entrySet()) {
            //定时任务不存在
            if(taskList.stream().map(DynamicTriggerTask::getNid).noneMatch(s -> s.equals(entry.getKey()))){
                entry.getValue().cancel(false);
            }
        }

        for (DynamicTriggerTask task : taskList){
            if(triggerTaskMap.containsKey(task.getNid()) && triggerTaskMap.get(task.getNid()).getCronExpression().equals(task.getCronExpression())){
                log.info("定时任务配置信息未发生变化 nid:[{}]",task.getNid());
                continue;
            }
            if(scheduledFutures.containsKey(task.getNid())){
                //定时任务存在,但配置发生变化 移除旧定时任务
                scheduledFutures.get(task.getNid()).cancel(false);
            }
            TaskScheduler scheduler = scheduledTaskRegistrar.getScheduler();
            if(scheduler != null){
                ScheduledFuture<?> schedule = scheduler.schedule(task.getRunnable(), task.getTrigger());
                scheduledFutures.put(task.getNid(),schedule);
            }
            triggerTaskMap.put(task.getNid(),task);
        }
    }

    @Override
    public void destroy(){
        //默认情况下 取消操作ScheduledTaskRegistrar#destroy,由于手动调用scheduler#schedule,因此不受ScheduledTaskRegistrar管理
        for (ScheduledFuture<?> future : scheduledFutures.values()) {
            if(future != null){
                future.cancel(true);
            }
        }
    }
}
