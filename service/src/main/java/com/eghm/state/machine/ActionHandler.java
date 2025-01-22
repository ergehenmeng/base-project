package com.eghm.state.machine;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.eghm.enums.event.IEvent;
import com.eghm.enums.ProductType;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
public interface ActionHandler<C extends Context> extends Action<Integer, IEvent, C>, Condition<C> {

    /**
     * 触发的事件类型
     *
     * @return 事件类型
     */
    IEvent getEvent();

    /**
     * 状态机类型
     *
     * @return 类型
     */
    ProductType getStateMachineType();

    /**
     * 判断是否满足条件
     * @param context context object
     * @return true
     */
    @Override
    default boolean isSatisfied(C context) {
        return true;
    }

    /**
     * 执行动作
     *
     * @param from 原状态
     * @param to 新状态
     * @param event 触发事件
     * @param context 上下文信息
     */
    @Override
    default void execute(Integer from, Integer to, IEvent event, C context) {
        context.setFrom(from);
        this.doAction(context);
    }

    /**
     * 真实业务处理
     *
     * @param context 上下文信息
     */
    void doAction(C context);

}
