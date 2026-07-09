package com.eghm.application.shared.configuration.log;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.constants.CommonConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

/**
 * 日志追踪线程变量, 保证在异步或者mq下依旧可以追中到消息
 *
 * @author 二哥很猛
 * @since 2023/3/20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogTraceHolder {

    private static final TransmittableThreadLocal<String> TTL_TRACE = new TransmittableThreadLocal<>() {

        /**
         * 在多线程数据传递的时候，将数据复制一份给MDC
         */
        @Override
        protected void beforeExecute() {
            String traceId = super.get();
            if (traceId != null) {
                MDC.put(CommonConstant.TRACE_ID, traceId);
            }
        }

        @Override
        protected void afterExecute() {
            MDC.remove(CommonConstant.TRACE_ID);
        }

    };

    public static void putTraceId(String value) {
        TTL_TRACE.set(value);
        MDC.put(CommonConstant.TRACE_ID, value);
    }

    public static String getTraceId() {
        return TTL_TRACE.get();
    }

    public static void clear() {
        TTL_TRACE.remove();
        MDC.remove(CommonConstant.TRACE_ID);
    }

}
