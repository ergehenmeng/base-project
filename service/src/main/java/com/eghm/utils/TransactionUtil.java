package com.eghm.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
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

    private static final TransactionTemplate TEMPLATE;

    static {
        TEMPLATE = SpringContextUtil.getBean(TransactionTemplate.class);
    }

    /**
     * 事务提交后置处理
     * @param runnable r
     */
    public static void afterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive() && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new AfterCommitHandler(runnable, null));
        } else {
            log.error("当前事务未同步,无法注册事件");
        }
    }

    /**
     * 事务提交后置处理
     * @param runnable r
     * @param consumer 如果runnable执行异常, 则执行该方法
     */
    public static void afterCommit(Runnable runnable, Consumer<Throwable> consumer) {
        if (TransactionSynchronizationManager.isActualTransactionActive() && TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new AfterCommitHandler(runnable, consumer));
        } else {
            log.error("当前事务未同步,无法注册事件");
        }
    }


    @AllArgsConstructor
    static class AfterCommitHandler implements TransactionSynchronization {

        private Runnable runnable;

        /**
         * 执行错误时的补偿操作
         */
        private Consumer<Throwable> consumer;

        @Override
        public void afterCompletion(int status) {
            if (status == STATUS_COMMITTED) {
                try {
                    runnable.run();
                } catch (Exception e) {
                    log.error("事务后置处理执行异常", e);
                    if (consumer != null) {
                        consumer.accept(e);
                    }
                }
            }
        }
    }

    /**
     * 手动事务提交, 减少线程持有事务的时间
     * @param runnable r
     */
    public static void manualCommit(Runnable runnable) {
        // 采用静态方式为了减少从容器中查询bean所消耗的时间
        TEMPLATE.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                runnable.run();
            }
        });
    }
}
