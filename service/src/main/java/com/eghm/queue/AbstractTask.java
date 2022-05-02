package com.eghm.queue;

import lombok.extern.slf4j.Slf4j;


/**
 * @param <T> 业务对象
 * @author 二哥很猛
 */
@Slf4j
public abstract class AbstractTask<T, B> implements Runnable {

    /**
     * 待处理的业务数据
     */
    private final T data;

    /**
     * 业务处理的Bean
     */
    private final B bean;

    protected AbstractTask(T data, B bean) {
        this.data = data;
        this.bean = bean;
    }

    @Override
    public void run() {
        if (log.isDebugEnabled()) {
            log.debug("队列任务[{}]开始执行", bean.getClass().getName());
        }
        try {
            this.execute(data);
        } catch (Exception e) {
            this.doException(e);
        } finally {
            this.doFinally();
        }
        if (log.isDebugEnabled()) {
            log.debug("队列任务[{}]执行结束", bean.getClass().getName());
        }
    }

    public T getData() {
        return data;
    }


    /**
     * 真实执行业务的逻辑
     *
     * @param data 传入的对象
     */
    protected abstract void execute(T data);

    /**
     * 任务执行错误处理
     *
     * @param e 错误信息
     */
    protected void doException(Exception e) {
        log.error("队列任务执行出现异常 data:[{}]", data, e);
    }

    /**
     * 执行完业务的后置处理操作
     */
    protected void doFinally() {
    }

    /**
     * 获取bean
     *
     * @return bean
     */
    protected B getBean() {
        return bean;
    }
}
