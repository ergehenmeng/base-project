package com.fanyin.handler;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/12/19 18:26
 */
public class HandlerInvoker<T extends Handler> {

    private int pos = 0;

    private int size;

    private List<T> handlerList;

    HandlerInvoker(List<T> handlerList) {
        this.handlerList = handlerList;
        this.size = handlerList.size();
    }

    public void doHandler(MessageData data) {
        if (pos < size) {
            Handler handler = handlerList.get(pos++);
            handler.doHandler(data, this);
        }
    }
}
