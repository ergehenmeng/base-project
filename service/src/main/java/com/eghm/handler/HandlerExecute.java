package com.eghm.handler;


import com.eghm.utils.SpringContextUtil;
import com.google.common.collect.Lists;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @date 2018/12/19 17:47
 */
@Service("handlerExecute")
public class HandlerExecute<T extends Handler> {

    /**
     * 存放bean的列表
     */
    private final Map<String, List<T>> handlerMap = new HashMap<>(32);

    /**
     * 执行业务逻辑
     *
     * @param messageData 传递参数对象
     * @param cls         处理器类型
     */
    public void execute(MessageData messageData, Class<T> cls) {
        List<T> handlers = getHandlers(cls);
        if (handlers != null) {
            new HandlerInvoker<>(handlers).doHandler(messageData);
        }
    }


    /**
     * 获取执行列表Bean
     *
     * @param cls bean类型 接口类型
     * @return 列表
     */
    private List<T> getHandlers(Class<T> cls) {
        List<T> handlerList = handlerMap.get(cls.getName());
        if (handlerList == null) {
            synchronized (handlerMap) {
                handlerList = handlerMap.get(cls.getName());
                if (handlerList == null) {
                    String[] beanNames = SpringContextUtil.getApplicationContext().getBeanNamesForType(cls);
                    if (beanNames.length > 0) {
                        handlerList = Lists.newArrayList();
                        for (String beanName : beanNames) {
                            T bean = SpringContextUtil.getApplicationContext().getBean(beanName, cls);
                            handlerList.add(bean);
                        }
                        AnnotationAwareOrderComparator.sort(handlerList);
                        handlerMap.put(cls.getName(), handlerList);
                    }
                }
            }
        }
        return handlerList;
    }

}
