package com.eghm.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author 二哥很猛
 * @since 2018/1/11 15:15
 */
@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class TransactionConfig {

    private static final String METHOD_EXPRESSION = "execution (* com.eghm.service..*.*(..))";

    @Bean
    @Primary
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public TransactionInterceptor txAdvice(@Lazy PlatformTransactionManager transactionManager, TransactionAttributeSource transactionAttributeSource) {
        DefaultTransactionAttribute required = new DefaultTransactionAttribute();
        required.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        DefaultTransactionAttribute readOnly = new DefaultTransactionAttribute();
        readOnly.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        readOnly.setReadOnly(true);
        NameMatchTransactionAttributeSource attributeSource = new NameMatchTransactionAttributeSource();
        attributeSource.addTransactionalMethod("add*", required);
        attributeSource.addTransactionalMethod("insert*", required);
        attributeSource.addTransactionalMethod("update*", required);
        attributeSource.addTransactionalMethod("edit*", required);
        attributeSource.addTransactionalMethod("set*", required);
        attributeSource.addTransactionalMethod("delete*", required);
        attributeSource.addTransactionalMethod("select*", readOnly);
        attributeSource.addTransactionalMethod("get*", readOnly);
        attributeSource.addTransactionalMethod("list*", readOnly);
        attributeSource.addTransactionalMethod("count*", readOnly);
        attributeSource.addTransactionalMethod("find*", readOnly);
        attributeSource.addTransactionalMethod("query*", readOnly);
        attributeSource.addTransactionalMethod("*", required);
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        // 设置优先级, 同时支持注解事务和声明式事务
        interceptor.setTransactionAttributeSources(transactionAttributeSource, attributeSource);
        return interceptor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public Advisor txAdvisor(TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(METHOD_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

}
