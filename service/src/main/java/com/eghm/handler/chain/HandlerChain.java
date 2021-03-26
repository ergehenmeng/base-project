package com.eghm.handler.chain;


import com.eghm.handler.chain.annotation.HandlerEnum;
import com.eghm.handler.chain.annotation.HandlerMark;
import com.eghm.utils.SpringContextUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2018/12/19 17:47
 */
@Service("handlerChain")
public class HandlerChain<T> implements CommandLineRunner {

    /**
     * 存放bean的列表
     */
    private final Map<HandlerEnum, List<Handler>> handlerMap = new EnumMap(HandlerEnum.class);

    /**
     * 执行业务逻辑
     *
     * @param messageData 传递参数对象
     * @param markName    处理器类型
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void execute(T messageData, HandlerEnum markName) {
        List<Handler> handlers = handlerMap.get(markName);
        if (handlers != null) {
            new HandlerInvoker<>(handlers).doHandler(messageData);
        }
    }

    @Override
    public void run(String... args) {
        // 如果提前获取上下文可能为空
        Map<String, Handler> beans = SpringContextUtil.getApplicationContext().getBeansOfType(Handler.class);
        for (Handler value : beans.values()) {
            HandlerMark annotation = AnnotationUtils.findAnnotation(value.getClass(), HandlerMark.class);
            if (annotation == null) {
                continue;
            }
            handlerMap.computeIfAbsent(annotation.value(), k -> new ArrayList<>(8)).add(value);
        }
        handlerMap.values().forEach(AnnotationAwareOrderComparator::sort);
    }
}
