package com.eghm.configuration.log;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.eghm.constant.CommonConstant;
import com.google.common.collect.Maps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.util.Map;

/**
 * 日志追踪线程变量, 保证在异步或者mq下依旧可以追中到消息
 *
 * @author 二哥很猛
 * @since 2023/3/20
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogTraceHolder {

    private static final TransmittableThreadLocal<Map<String, String>> TTL_MDC = new TransmittableThreadLocal<Map<String, String>>() {

        /**
         * 在多线程数据传递的时候，将数据复制一份给MDC
         */
        @Override
        protected void beforeExecute() {
            Map<String, String> mdc = super.get();
            mdc.forEach(MDC::put);
        }

        @Override
        protected void afterExecute() {
            MDC.clear();
        }

        @Override
        protected Map<String, String> initialValue() {
            return Maps.newHashMapWithExpectedSize(4);
        }
    };

    public static void put(String value) {
        TTL_MDC.get().put(CommonConstant.TRACE_ID, value);
    }

    public static String get() {
        return TTL_MDC.get().get(CommonConstant.TRACE_ID);
    }

    public static void clear() {
        TTL_MDC.get().clear();
        TTL_MDC.remove();
    }

}
