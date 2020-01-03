package com.fanyin.handler;


/**
 * 支持@Order注解,值越小,越靠前
 *
 * @author 二哥很猛
 * @date 2018/12/19 17:46
 */
public interface Handler {

    /**
     * 执行业务逻辑
     *
     * @param messageData 传输对象
     * @param invoker     调用链
     */
    void doHandler(MessageData messageData, HandlerInvoker invoker);
}

