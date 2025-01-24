package com.eghm.utils;

import com.eghm.common.AlarmService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Consumer;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class TransactionUtil {

    public static final AlarmService ALARM_SERVICE;

    private static final TransactionTemplate TEMPLATE;

    static {
        ALARM_SERVICE = SpringContextUtil.getBean(AlarmService.class);
        TEMPLATE = SpringContextUtil.getBean(TransactionTemplate.class);
    }

    /**
     * 事务提交后置处理
     *
     * @param runnable r
     */
    public static void afterCommit(Runnable runnable) {
        afterCommit(runnable, null);
    }

    /**
     * 事务提交后置处理
     *
     * @param runnable r
     * @param consumer 如果runnable执行异常, 则执行该方法
     */
    public static void afterCommit(Runnable runnable, Consumer<Throwable> consumer) {
        if (TransactionSynchronizationManager.isSynchronizationActive() && TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new AfterCommitHandler(runnable, consumer));
        } else {
            log.error("当前事务未同步,无法注册事件 [{}]", consumer);
        }
    }

    /**
     * 手动事务提交, 减少线程持有事务的时间
     *
     * @param runnable r
     */
    public static void manualCommit(Runnable runnable) {
        // 采用静态方式为了减少从容器中查询bean所消耗的时间
        TEMPLATE.execute(status -> {
            runnable.run();
            return null;
        });
    }

    @AllArgsConstructor
    static class AfterCommitHandler implements TransactionSynchronization {

        private final Runnable runnable;

        /**
         * 执行错误时的补偿操作, 注意:由于事务提交后, 进行处理时,spring默认会把异常吃掉, 因此afterCompletion调用发生的异常需要通过消息的方式发给调用方
         */
        private final Consumer<Throwable> consumer;

        @Override
        public void afterCompletion(int status) {
            if (status == STATUS_COMMITTED) {
                try {
                    runnable.run();
                } catch (Exception e) {
                    log.error("事务后置处理执行异常, 尝试进行补充操作 [{}]", consumer, e);
                    if (consumer != null) {
                        try {
                            consumer.accept(e);
                        } catch (Exception e2) {
                            log.error("事务后置处理执行异常, 补充机制依旧异常", e2);
                            ALARM_SERVICE.sendMsg(String.format("事务后置处理执行异常,补充机制依旧异常, 错误信息: %s", ExceptionUtils.getStackTrace(e2)));
                        }
                    } else {
                        String errorMsg = ExceptionUtils.getStackTrace(e);
                        ALARM_SERVICE.sendMsg(String.format("事务后置处理执行异常,且没有补充机制, 错误信息: %s", errorMsg));
                    }
                }
            }
        }
    }
}
