package com.eghm.state.machine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.eghm.common.enums.StateMachineType;
import com.eghm.common.enums.event.IEvent;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface ActionHandler<C extends Context> extends Action<Integer, IEvent, C>, Condition<C> {

    /**
     * 触发的事件类型
     * @return 事件类型
     */
    IEvent getEvent();

    /**
     * 状态机名称
     * @return 名称
     */
    StateMachineType getStateMachineType();

    @Override
    default boolean isSatisfied(C context) {
        return true;
    }
}
