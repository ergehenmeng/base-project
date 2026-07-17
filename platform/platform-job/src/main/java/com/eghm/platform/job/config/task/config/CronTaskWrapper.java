package com.eghm.platform.job.config.task.config;

import com.eghm.foundation.core.service.AlarmService;
import com.eghm.foundation.core.constants.CommonConstant;
import com.eghm.foundation.core.lock.RedisLock;
import com.eghm.platform.job.entity.SysTask;
import com.eghm.platform.job.service.SysTaskLogService;
import com.eghm.foundation.web.utility.DataUtil;
import lombok.Getter;
import org.springframework.scheduling.config.CronTask;

/**
 * @author 二哥很猛
 * @since 2019/9/6 14:54
 */
@Getter
public class CronTaskWrapper extends CronTask {

    /**
     * 任务的唯一id用于打印日志等
     */
    private final String nid;

    CronTaskWrapper(SysTask task, RedisLock redisLock, AlarmService alarmService, SysTaskLogService sysTaskLogService) {
        super(new Invoker(DataUtil.copy(task, CronScheduleBean.class), redisLock, alarmService, sysTaskLogService), task.getCronExpression());
        this.nid = task.getBeanName() + CommonConstant.SPECIAL_SPLIT + task.getMethodName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CronTaskWrapper that = (CronTaskWrapper) o;
        return nid != null && nid.equals(that.nid);
    }

    @Override
    public int hashCode() {
        return nid != null ? nid.hashCode() : 0;
    }
}