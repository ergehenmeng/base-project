package com.eghm.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

/**
 * @author 二哥很猛
 * @date 2018/1/11 15:15
 */
@Configuration
@Aspect
public class HikariDataSourceConfiguration {

    @Value("${spring.datasource.hikari.jdbc-url}")
    private String url;

    @Value("${spring.datasource.hikari.username}")
    private String username;

    @Value("${spring.datasource.hikari.password}")
    private String password;

    @Value("${spring.datasource.hikari.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.hikari.idle-timeout}")
    private int minIdle;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int maxPoolSize;

    private static final String METHOD_EXPRESSION = "execution (* com.eghm.service..*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;


    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMinimumIdle(minIdle);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setMaximumPoolSize(maxPoolSize);
        return dataSource;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
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
    public Advisor txAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(METHOD_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}
