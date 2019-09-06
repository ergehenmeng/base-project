package com.fanyin.configuration.job;

import cn.hutool.core.bean.BeanException;
import com.fanyin.common.exception.BusinessException;
import com.fanyin.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 二哥很猛
 * @date 2019/9/6 15:27
 */
@Slf4j
public class DynamicRunnable implements Runnable {

    private Task task;

    private String beanName;

    DynamicRunnable(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void run() {
        getTask().execute();
    }

    public Task getTask() {
        if(task != null){
            return task;
        }
        try {
            this.task = (Task)SpringContextUtil.getBean(beanName);
        }catch (BeanException e){
            log.error("创建定时任务异常 beanName:[{}]",beanName,e);
            throw new BusinessException(-1,"定时任务创建Bean失败");
        }
        return task;
    }
}
