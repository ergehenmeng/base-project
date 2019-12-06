package com.fanyin.queue;

import lombok.extern.slf4j.Slf4j;


/**
 * @author 二哥很猛
 * @param <T> 业务对象
 */
@Slf4j
public abstract class AbstractTask<T,B> implements Runnable{

    /**
     * 待处理的业务数据
     */
    private T data;

    /**
     * 业务处理的Bean
     */
    private B bean;

    public AbstractTask(T data,B bean){
        this.data = data;
        this.bean = bean;
    }

    @Override
    public void run() {
        log.debug("队列任务开始执行");
        try {
            this.execute(data);
        }catch (Exception e){
            this.doException(e);
        }finally {
            this.doFinally();
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
        log.error("队列任务执行出现异常 data:[{}]",data,e);
    }

    /**
     * 执行完业务的后置处理操作
     */
    protected void doFinally(){
        log.debug("队列任务执行完成");
    }

    /**
     * 获取bean
     * @return bean
     */
    protected B getBean(){
        return bean;
    }
}
