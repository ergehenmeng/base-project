package com.eghm.state.machine.handler;

import com.eghm.common.enums.IEvent;
import com.eghm.state.machine.ActionHandler;
import com.eghm.state.machine.context.LineContext;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
@Slf4j
@Component
public class LineHandler implements ActionHandler<LineContext> {

    @Override
    public List<Integer> getFromState() {
        return Lists.newArrayList(1);
    }

    @Override
    public Integer getToState() {
        return 2;
    }

    @Override
    public IEvent getEvent() {
        return IEvent.PAY;
    }

    @Override
    public String getMachineName() {
        return "line";
    }

    @Override
    public void execute(Integer from, Integer to, IEvent event, LineContext context) {
        log.info(" [{}] [{}] [{}] [{}]", from, to, event, context);
    }

}
