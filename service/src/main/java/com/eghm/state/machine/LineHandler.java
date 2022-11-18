package com.eghm.state.machine;

import com.eghm.common.enums.IEvent;
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
public class LineHandler extends ActionHandler<LineContext> {

    @Override
    List<Integer> getFromState() {
        return Lists.newArrayList(1);
    }

    @Override
    Integer getToState() {
        return 2;
    }

    @Override
    IEvent getEvent() {
        return IEvent.PAY;
    }

    @Override
    String getMachineName() {
        return "line";
    }

    @Override
    public void execute(Integer from, Integer to, IEvent event, LineContext context) {
        log.info(" [{}] [{}] [{}] [{}]", from, to, event, context);
    }

}
