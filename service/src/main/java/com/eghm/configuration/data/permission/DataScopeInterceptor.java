package com.eghm.configuration.data.permission;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/9/22
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
public class DataScopeInterceptor implements Interceptor {

    private static final String DATA_SCOPE = "dataScope";

    private static final int DEFAULT_ARGS_LENGTH = 4;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Object intercept(Invocation invocation) throws SQLException {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        Object paramObject = args[1];
        RowBounds rowBounds = (RowBounds) args[2];
        ResultHandler resultHandler = (ResultHandler) args[3];

        Executor executor = (Executor) invocation.getTarget();
        Map<String, Object> paramMap;
        if (paramObject == null) {
            paramMap = new HashMap<>(4);
        } else if (paramObject instanceof Map map) {
            paramMap = map;
        } else {
            paramMap = new HashMap<>(8);
            boolean hasTypeHandler = ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(paramObject.getClass());
            MetaObject metaObject = SystemMetaObject.forObject(paramObject);
            if (!hasTypeHandler) {
                for (String name : metaObject.getGetterNames()) {
                    paramMap.put(name, metaObject.getValue(name));
                }
            } else {
                BoundSql boundSql = ms.getBoundSql(paramObject);
                for (ParameterMapping mapping : boundSql.getParameterMappings()) {
                    paramMap.put(mapping.getProperty(), paramObject);
                }
            }
        }
        paramMap.put(DATA_SCOPE, DataScopeAspect.getScope());
        BoundSql boundSql;
        CacheKey cacheKey;
        if (args.length == DEFAULT_ARGS_LENGTH) {
            boundSql = ms.getBoundSql(paramMap);
            cacheKey = executor.createCacheKey(ms, paramMap, rowBounds, boundSql);
        } else {
            cacheKey = (CacheKey) args[4];
            boundSql = (BoundSql) args[5];
        }
        return executor.query(ms, paramMap, rowBounds, resultHandler, cacheKey, boundSql);
    }

}
