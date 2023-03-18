package com.eghm.state.machine;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.event.IEvent;
import com.eghm.common.enums.ref.ProductType;
import com.eghm.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
@Service("stateHandler")
@Slf4j
@AllArgsConstructor
public class StateHandler {

    private final EnumMap<ProductType, StateMachine<Integer, IEvent, Context>> machineMap = new EnumMap<>(ProductType.class);

    private final List<ActionHandler<? extends Context>> handlerList;

    @PostConstruct
    public void init() {
        this.registerStateMachine(ProductType.TICKET);
    }

    /**
     * 注册状态机
     * @param machineType 状态机类型
     */
    public void registerStateMachine(ProductType machineType) {
        if (CollUtil.isEmpty(handlerList)) {
            log.info("未发现状态机处理类 [{}]", machineType);
            return;
        }
        StateMachineBuilder<Integer, IEvent, Context> builder = StateMachineBuilderFactory.create();
        for (ActionHandler<? extends Context> handler : handlerList) {
            if (machineType == handler.getStateMachineType()) {
                IEvent event = handler.getEvent();
                if (event != null) {
                    for (Integer from : event.from()) {
                        builder.externalTransition().from(from).to(event.to()).on(handler.getEvent()).when((Condition<Context>) handler).perform((Action<Integer, IEvent, Context>) handler);
                    }
                }
            }
        }
        StateMachine<Integer, IEvent, Context> machine = builder.build(machineType.name());
        machineMap.put(machineType, machine);
    }

    /**
     * 执行流程
     * @param machineType 状态机名称
     * @param from 原状态
     * @param event 事件
     * @param context 上下文内容
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void fireEvent(ProductType machineType, Integer from, IEvent event, Context context) {
        this.getStateMachine(machineType).fireEvent(from, event, context);
    }

    /**
     * 获取状态机
     * @param machineType 状态机名称
     * @return 状态机
     */
    public StateMachine<Integer, IEvent, Context> getStateMachine(ProductType machineType) {
        com.alibaba.cola.statemachine.StateMachine<Integer, IEvent, Context> machine = machineMap.get(machineType);
        if (machine == null) {
            throw new BusinessException(ErrorCode.STATE_MACHINE_REGISTER.getCode(), String.format(ErrorCode.STATE_MACHINE_REGISTER.getMsg(), machineType.name()));
        }
        return machine;
    }

}
