package com.eghm.state.machine;

import com.alibaba.cola.statemachine.Action;
import com.eghm.common.enums.IEvent;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public abstract class ActionHandler<T extends Context> implements Action<Integer, IEvent, T> {

    /**
     * 起始状态
     * @return 多个
     */
    abstract List<Integer> getFromState();

    /**
     * 最终状态
     * @return 1个
     */
    abstract Integer getToState();

    /**
     * 触发的事件类型
     * @return 事件类型
     */
    abstract IEvent getEvent();

    /**
     * 状态机名称
     * @return 名称
     */
    abstract String getMachineName();
}
