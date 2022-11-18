package com.eghm.state.machine;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.IEvent;
import com.eghm.common.exception.BusinessException;
import com.eghm.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
@Component
@Slf4j
@AllArgsConstructor
public class StatMachineAccess {

    private final Map<String, StateMachine<Integer, IEvent, Context>> machineMap = new ConcurrentHashMap<>();

    private final List<ActionHandler<Context>> handlerList;

    @PostConstruct
    public void init() {
        this.registerStateMachine("line");
    }

    /**
     * 注册状态机
     * @param machineName 状态机名称
     */
    public void registerStateMachine(String machineName) {
        if (CollUtil.isEmpty(handlerList)) {
            log.info("未发现状态机处理类 [{}]", machineName);
            return;
        }
        StateMachineBuilder<Integer, IEvent, Context> builder = StateMachineBuilderFactory.create();
        for (ActionHandler<Context> handler : handlerList) {
            if (machineName.equals(handler.getMachineName())) {
                for (Integer from : handler.getFromState()) {
                    builder.externalTransition().from(from).to(handler.getToState()).on(handler.getEvent()).perform(handler);
                }
            }
        }
        StateMachine<Integer, IEvent, Context> machine = builder.build(machineName);
        machineMap.put(machineName, machine);
    }

    /**
     * 执行流程
     * @param machineName 状态机名称
     * @param from 原状态
     * @param event 事件
     * @param context 上下文内容
     */
    public void fireEvent(String machineName, Integer from, IEvent event, Context context) {
        this.getStateMachine(machineName).fireEvent(from, event, context);
    }

    /**
     * 获取状态机
     * @param machineName 状态机名称
     * @return 状态机
     */
    public StateMachine<Integer, IEvent, Context> getStateMachine(String machineName) {
        StateMachine<Integer, IEvent, Context> machine = machineMap.get(machineName);
        if (machine == null) {
            throw new BusinessException(ErrorCode.STATE_MACHINE_REGISTER.getCode(), String.format(ErrorCode.STATE_MACHINE_REGISTER.getMsg(), machineName));
        }
        return machine;
    }

}
