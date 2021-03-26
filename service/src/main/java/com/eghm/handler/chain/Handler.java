package com.eghm.handler.chain;


/**
 * 支持@Order注解,值越小,越靠前
 *
 * @author 二哥很猛
 * @date 2018/12/19 17:46
 */
public interface Handler<T> {

    /**
     * 执行业务逻辑
     *
     * @param messageData 传输对象
     * @param invoker     调用链
     */
    void doHandler(T messageData, HandlerInvoker<T> invoker);
}

