package com.eghm.handler.chain;

import java.util.List;

/**
 * @author 二哥很猛
 * @date 2018/12/19 18:26
 */
public class HandlerInvoker {

    private int pos = 0;

    private final int size;

    private final List<Handler> handlerList;

    HandlerInvoker(List<Handler> handlerList) {
        this.handlerList = handlerList;
        this.size = handlerList.size();
    }

    public void doHandler(Object data) {
        if (pos < size) {
            Handler handler = handlerList.get(pos++);
            handler.doHandler(data, this);
        }
    }
}
