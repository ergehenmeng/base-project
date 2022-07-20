package com.eghm.configuration;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * @author 二哥很猛
 * @date 2018/1/11 15:15
 */
@Configuration
@AutoConfigureAfter(value = {ProxyTransactionManagementConfiguration.class, TransactionAutoConfiguration.class})
public class TransactionConfig {

    private static final String METHOD_EXPRESSION = "execution (* com.eghm.service..*.*(..))";

    @Bean
    @Primary
    public TransactionInterceptor txAdvice(TransactionManager transactionManager) {
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
        return new TransactionInterceptor(transactionManager, attributeSource);
    }

    @Bean
    public Advisor txAdvisor(TransactionInterceptor transactionInterceptor) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(METHOD_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, transactionInterceptor);
    }

}
