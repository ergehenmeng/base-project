package com.eghm.state.machine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.eghm.common.enums.IEvent;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface ActionHandler<C extends Context> extends Action<Integer, IEvent, C>, Condition<C> {

    /**
     * 起始状态
     * @return 多个
     */
    List<Integer> getFromState();

    /**
     * 最终状态
     * @return 1个
     */
    Integer getToState();

    /**
     * 触发的事件类型
     * @return 事件类型
     */
    IEvent getEvent();

    /**
     * 状态机名称
     * @return 名称
     */
    String getMachineName();

    @Override
    default boolean isSatisfied(C context) {
        return true;
    }
}
