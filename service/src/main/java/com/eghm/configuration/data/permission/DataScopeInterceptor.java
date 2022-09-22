package com.eghm.configuration.data.permission;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2022/9/22
 */
public class DataScopeInterceptor implements InnerInterceptor {

    private static final String DATA_SCOPE = "dataScope";

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
        Map<String, Object> objectMap = mpBoundSql.additionalParameters();
        objectMap.put(DATA_SCOPE, DataScopeAspect.getScope());
        List<ParameterMapping> mappingList = mpBoundSql.parameterMappings();
        mappingList.add(new ParameterMapping.Builder(ms.getConfiguration(), DATA_SCOPE, String.class).build());
        mpBoundSql.parameterMappings(mappingList);
    }
}
