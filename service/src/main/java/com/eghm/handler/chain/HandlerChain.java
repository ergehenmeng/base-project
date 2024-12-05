package com.eghm.handler.chain;


import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import com.eghm.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * 注意: com.eg.hm.handler 包下所有的类并未被事务管理,因此请自行保证业务逻辑的完整性
 *
 * @author 二哥很猛
 * @since 2018/12/19 17:47
 */
@Slf4j
@Service("handlerChain")
public class HandlerChain implements CommandLineRunner {

    /**
     * 存放bean的列表
     */
    private final Map<HandlerEnum, List<Handler>> handlerMap = new EnumMap<>(HandlerEnum.class);

    /**
     * 执行业务逻辑
     *
     * @param messageData 传递参数对象
     * @param markName    处理器类型
     */
    public void execute(Object messageData, HandlerEnum markName) {
        List<Handler> handlers = handlerMap.get(markName);
        if (handlers != null) {
            new HandlerInvoker(handlers).doHandler(messageData);
        }
    }

    @Override
    public void run(String... args) {
        // 如果提前获取上下文可能为空
        Map<String, Handler> beans = SpringContextUtil.getApplicationContext().getBeansOfType(Handler.class);
        for (Handler value : beans.values()) {
            HandlerMark annotation = AnnotationUtils.findAnnotation(value.getClass(), HandlerMark.class);
            if (annotation == null) {
                log.warn("[{}]实现了Handler接口, 但没有@HandlerMark注解", value.getClass().getName());
                continue;
            }
            handlerMap.computeIfAbsent(annotation.value(), k -> new ArrayList<>(8)).add(value);
        }
        handlerMap.values().forEach(AnnotationAwareOrderComparator::sort);
    }
}
