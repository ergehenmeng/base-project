package com.fanyin.queue;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 二哥很猛
 * @param <T> 业务对象
 */
@Slf4j
public abstract class AbstractTask<T,B> implements Runnable{

    private T data;

    private B bean;

    public AbstractTask(T data,B bean){
        this.data = data;
        this.bean = bean;
    }

    @Override
    public void run() {
        log.debug("队列任务开始执行");
        try {
            execute(data);
        }catch (Exception e){
            this.doException(e);
        }
        log.debug("队列任务执行结束");
    }

    public T getData() {
        return data;
    }


    /**
     * 真实执行业务的逻辑
     * @param data 传入的对象
     */
    protected abstract void execute(T data);

    /**
     * 任务执行错误处理
     * @param e 错误信息
     */
    protected void doException(Exception e){
        log.error("队列任务执行异常",e);
    }

    /**
     * 获取bean
     * @return bean
     */
    protected B getBean(){
        return bean;
    }
}
