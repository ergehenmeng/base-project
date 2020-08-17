package com.eghm.configuration.data.permission;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;

/**
 * @author 殿小二
 * @date 2020/8/17
 */
@Component
@Intercepts({
@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        })
@Slf4j
public class DataScopeInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String dataSql = DataScopeAspect.getSql();
        if (StrUtil.isBlank(dataSql)) {
            return invocation.proceed();
        }
        Object[] args = invocation.getArgs();
        BoundSql boundSql;
        if (args.length == 4) {
            MappedStatement ms = (MappedStatement) args[0];
            boundSql = ms.getBoundSql(args[1]);
        } else {
            boundSql = (BoundSql) args[5];
        }
        MetaObject forObject = SystemMetaObject.forObject(boundSql);
        Map<String, Object> additionalParameters = (Map<String, Object>) forObject.getValue("additionalParameters");
        additionalParameters.putIfAbsent("dataScope", dataSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
