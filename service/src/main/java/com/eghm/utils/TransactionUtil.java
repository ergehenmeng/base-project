package com.eghm.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author 二哥很猛
 * @since 2022/11/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionUtil {

    /**
     * 事务提交后置处理
     * @param runnable r
     */
    public static void afterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new AfterCommitHandler(runnable));
        }
    }

    @AllArgsConstructor
    static class AfterCommitHandler implements TransactionSynchronization {

        private Runnable runnable;

        @Override
        public void afterCompletion(int status) {
            if (status == STATUS_COMMITTED) {
                runnable.run();
            }
        }
    }
}
