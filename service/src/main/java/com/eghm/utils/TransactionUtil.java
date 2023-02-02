package com.eghm.utils;

import com.eghm.common.enums.ref.OrderState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

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

    /**
     * 手动事务提交, 减少线程持有事务的时间
     * @param runnable r
     */
    public static void manualCommit(Runnable runnable) {
        TransactionTemplate template = SpringContextUtil.getBean(TransactionTemplate.class);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                runnable.run();
            }
        });
    }
}
