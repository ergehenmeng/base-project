package com.eghm.infrastructure.persistence.mybatis.handler;

import com.eghm.utils.EnumValueUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Persists domain enums by value/code without MyBatis annotations in domain.
 */
@MappedTypes(Enum.class)
public class DomainEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> enumType;

    public DomainEnumTypeHandler(Class<E> enumType) {
        this.enumType = enumType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, EnumValueUtil.value(parameter));
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return EnumValueUtil.fromValue(enumType, rs.getObject(columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return EnumValueUtil.fromValue(enumType, rs.getObject(columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return EnumValueUtil.fromValue(enumType, cs.getObject(columnIndex));
    }
}
