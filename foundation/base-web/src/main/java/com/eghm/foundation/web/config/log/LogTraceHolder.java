package com.eghm.foundation.web.config.log;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.foundation.core.constants.CommonConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * 日志追踪线程变量, 保证在异步或者mq下依旧可以追中到消息
 *
 * @author 二哥很猛
 * @since 2023/3/20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogTraceHolder {

    private static final TransmittableThreadLocal<Map<String, String>> TTL_CONTEXT = new TransmittableThreadLocal<>() {
        
        @Override
        public Map<String, String> copy(Map<String, String> parentValue) {
            if  (parentValue == null) {
                parentValue = new HashMap<>(4);
            }
            return new HashMap<>(parentValue);
        }
        
        @Override
        protected Map<String, String> childValue(Map<String, String> parentValue) {
            return this.copy(parentValue);
        }
        
        /**
         * 在多线程数据传递的时候，将数据复制一份给MDC
         */
        @Override
        protected void beforeExecute() {
            Map<String, String> map = super.get();
            if (map != null) {
                MDC.setContextMap(map);
            }
        }

        @Override
        protected void afterExecute() {
            MDC.clear();
        }
        
        @Override
        protected Map<String, String> initialValue() {
            return new HashMap<>(4);
        }
    };

    public static void putAll(String traceId, String clientIp) {
        Map<String, String> map = TTL_CONTEXT.get();
        map.put(CommonConstant.TRACE_ID, traceId);
        MDC.put(CommonConstant.TRACE_ID, traceId);
        map.put(CommonConstant.CLIENT_IP, clientIp);
        MDC.put(CommonConstant.CLIENT_IP, clientIp);
    }
    
    public static String getTraceId() {
        return TTL_CONTEXT.get().get(CommonConstant.TRACE_ID);
    }
    
    public static String getClientIp() {
        return TTL_CONTEXT.get().get(CommonConstant.CLIENT_IP);
    }
    
    public static Map<String, String> getContext() {
        return TTL_CONTEXT.get();
    }

    public static void clear() {
        TTL_CONTEXT.remove();
        MDC.clear();
    }

}
