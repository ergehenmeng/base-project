package com.eghm.state.machine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ref.ProductType;

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
     * 状态机类型
     * @return 类型
     */
    ProductType getStateMachineType();

    @Override
    default boolean isSatisfied(C context) {
        return true;
    }

    @Override
    default void execute(Integer from, Integer to, IEvent event, C context) {
        context.setFrom(from);
        context.setTo(to);
        this.doAction(context);
    }

    /**
     * 真实业务处理
     * @param context 上下文信息
     */
    void doAction(C context);

}
