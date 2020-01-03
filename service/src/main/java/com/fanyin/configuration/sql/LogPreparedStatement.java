package com.fanyin.configuration.sql;

import org.slf4j.Logger;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author 二哥很猛
 * @date 2019/10/16 20:09
 */
public class LogPreparedStatement implements PreparedStatement {

    private String sql;

    private final List<String> argsList = new ArrayList<>();
    private final List<String> batchSql = new ArrayList<>();

    private DataResolve dataResolve;

    private PreparedStatement delegate;

    private static final Logger LOGGER = SqlLogFactory.getLogger();

    public LogPreparedStatement(String sql, DataResolve dataResolve, PreparedStatement preparedStatement) {
        this.sql = sql;
        this.dataResolve = dataResolve;
        this.delegate = preparedStatement;
    }

    private void recordArgs(int index, Object param) {
        String args;
        try {
            args = dataResolve.resolve(param);
        } catch (Exception e) {
            args = param == null ? "Null" : param.toString();
        }
        index--;
        synchronized (argsList) {
            while (index >= argsList.size()) {
                argsList.add(argsList.size(), null);
            }
            argsList.set(index, args);
        }
    }

    private void clearRecordArgs() {
        synchronized (argsList) {
            argsList.clear();
        }
    }


    @Override
    public ResultSet executeQuery() throws SQLException {
        LOGGER.info(this.getResolveSql());
        return delegate.executeQuery();
    }

    @Override
    public int executeUpdate() throws SQLException {
        LOGGER.info(this.getResolveSql());
        return delegate.executeUpdate();
    }

    private String getResolveSql() {
        StringBuilder dumpSql = new StringBuilder();
        int lastPos = 0;
        // find position of first question mark
        int pos = sql.indexOf('?', lastPos);
        int argIdx = 0;
        String arg;

        while (pos != -1) {
            // get stored argument
            synchronized (argsList) {
                try {
                    arg = argsList.get(argIdx);
                } catch (IndexOutOfBoundsException e) {
                    arg = "?";
                }
            }
            if (arg == null) {
                arg = "?";
            }

            argIdx++;
            // dump segment of sql up to question mark.
            dumpSql.append(sql.substring(lastPos, pos));
            lastPos = pos + 1;
            pos = sql.indexOf('?', lastPos);
            dumpSql.append(arg);
        }
        if (lastPos < sql.length()) {
            // dump last segment
            dumpSql.append(sql.substring(lastPos));
        }

        return dumpSql.toString();
    }

    @Override
    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        this.recordArgs(parameterIndex, sqlType);
        delegate.setNull(parameterIndex, sqlType);
    }

    @Override
    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setBoolean(parameterIndex, x);
    }

    @Override
    public void setByte(int parameterIndex, byte x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setByte(parameterIndex, x);
    }

    @Override
    public void setShort(int parameterIndex, short x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setShort(parameterIndex, x);
    }

    @Override
    public void setInt(int parameterIndex, int x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setInt(parameterIndex, x);
    }

    @Override
    public void setLong(int parameterIndex, long x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setLong(parameterIndex, x);
    }

    @Override
    public void setFloat(int parameterIndex, float x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setFloat(parameterIndex, x);
    }

    @Override
    public void setDouble(int parameterIndex, double x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setDouble(parameterIndex, x);
    }

    @Override
    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setBigDecimal(parameterIndex, x);
    }

    @Override
    public void setString(int parameterIndex, String x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setString(parameterIndex, x);
    }

    @Override
    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        this.recordArgs(parameterIndex, "<byte[]>");
        delegate.setBytes(parameterIndex, x);
    }

    @Override
    public void setDate(int parameterIndex, Date x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setDate(parameterIndex, x);
    }

    @Override
    public void setTime(int parameterIndex, Time x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setTime(parameterIndex, x);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setTimestamp(parameterIndex, x);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.recordArgs(parameterIndex, "<Ascii Stream>");
        delegate.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        this.recordArgs(parameterIndex, "<Binary Stream>");
        delegate.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void clearParameters() throws SQLException {
        this.clearRecordArgs();
        delegate.clearParameters();
    }


    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setObject(parameterIndex, x, targetSqlType);
    }

    @Override
    public void setObject(int parameterIndex, Object x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setObject(parameterIndex, x);
    }

    @Override
    public boolean execute() throws SQLException {
        LOGGER.info(this.getResolveSql());
        delegate.execute();
        return false;
    }

    @Override
    public void addBatch() throws SQLException {
        batchSql.add(this.getResolveSql());
        delegate.addBatch();
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        this.recordArgs(parameterIndex, "<Character Stream>");
        delegate.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setRef(int parameterIndex, Ref x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setRef(parameterIndex, x);
    }

    @Override
    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        this.recordArgs(parameterIndex, "<Blob>");
        delegate.setBlob(parameterIndex, x);
    }

    @Override
    public void setClob(int parameterIndex, Clob x) throws SQLException {
        this.recordArgs(parameterIndex, "<Clob>");
        delegate.setClob(parameterIndex, x);
    }

    @Override
    public void setArray(int parameterIndex, Array x) throws SQLException {
        this.recordArgs(parameterIndex, "<Array>");
        delegate.setArray(parameterIndex, x);
    }

    @Override
    public ResultSetMetaData getMetaData() throws SQLException {
        return delegate.getMetaData();
    }

    @Override
    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setDate(parameterIndex, x, cal);
    }

    @Override
    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setTime(parameterIndex, x, cal);
    }

    @Override
    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setTimestamp(parameterIndex, x, cal);
    }

    @Override
    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        this.recordArgs(parameterIndex, null);
        delegate.setNull(parameterIndex, sqlType, typeName);
    }

    @Override
    public void setURL(int parameterIndex, URL x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setURL(parameterIndex, x);
    }

    @Override
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return delegate.getParameterMetaData();
    }

    @Override
    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setRowId(parameterIndex, x);
    }

    @Override
    public void setNString(int parameterIndex, String value) throws SQLException {
        this.recordArgs(parameterIndex, value);
        delegate.setNString(parameterIndex, value);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<NCharacter Stream>");
        delegate.setNCharacterStream(parameterIndex, value, length);
    }

    @Override
    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        this.recordArgs(parameterIndex, "<NClob>");
        delegate.setNClob(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<Clob>");
        delegate.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<Blob>");
        delegate.setBlob(parameterIndex, inputStream, length);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<NClob>");
        delegate.setNClob(parameterIndex, reader, length);
    }

    @Override
    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        this.recordArgs(parameterIndex, xmlObject);
        delegate.setSQLXML(parameterIndex, xmlObject);
    }

    @Override
    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        this.recordArgs(parameterIndex, x);
        delegate.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<Ascii Stream>");
        delegate.setAsciiStream(parameterIndex, x, length);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<Binary Stream>");
        delegate.setBinaryStream(parameterIndex, x, length);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        this.recordArgs(parameterIndex, "<Character Stream>");
        delegate.setCharacterStream(parameterIndex, reader, length);
    }

    @Override
    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        this.recordArgs(parameterIndex, "<Ascii Stream>");
        delegate.setAsciiStream(parameterIndex, x);
    }

    @Override
    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        this.recordArgs(parameterIndex, "<Binary Stream>");
        delegate.setBinaryStream(parameterIndex, x);
    }

    @Override
    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        this.recordArgs(parameterIndex, "<Character Stream>");
        delegate.setCharacterStream(parameterIndex, reader);
    }

    @Override
    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        this.recordArgs(parameterIndex, "<NCharacter Stream>");
        delegate.setNCharacterStream(parameterIndex, value);
    }

    @Override
    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        this.recordArgs(parameterIndex, "<Clob>");
        delegate.setClob(parameterIndex, reader);
    }

    @Override
    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        this.recordArgs(parameterIndex, "<Blob>");
        delegate.setBlob(parameterIndex, inputStream);
    }

    @Override
    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        this.recordArgs(parameterIndex, "<NClob>");
        delegate.setNClob(parameterIndex, reader);
    }

    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        LOGGER.info(sql);
        return delegate.executeQuery(sql);
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        LOGGER.info(sql);
        return delegate.executeUpdate(sql);
    }

    @Override
    public void close() throws SQLException {
        delegate.close();
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return delegate.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        delegate.setMaxFieldSize(max);
    }

    @Override
    public int getMaxRows() throws SQLException {
        return delegate.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        delegate.setMaxRows(max);
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        delegate.setEscapeProcessing(enable);
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return delegate.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        delegate.setQueryTimeout(seconds);
    }

    @Override
    public void cancel() throws SQLException {
        delegate.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return delegate.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        delegate.clearWarnings();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        delegate.setCursorName(name);
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        LOGGER.info(sql);
        return delegate.execute(sql);
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return delegate.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return delegate.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        return delegate.getMoreResults();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        delegate.setFetchDirection(direction);
    }

    @Override
    public int getFetchDirection() throws SQLException {
        return delegate.getFetchDirection();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        delegate.setFetchSize(rows);
    }

    @Override
    public int getFetchSize() throws SQLException {
        return delegate.getFetchSize();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        return delegate.getResultSetConcurrency();
    }

    @Override
    public int getResultSetType() throws SQLException {
        return delegate.getResultSetType();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        LOGGER.info(sql);
        delegate.addBatch(sql);
    }

    @Override
    public void clearBatch() throws SQLException {
        batchSql.clear();
        delegate.clearBatch();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        LOGGER.info("批量更新SQL:");
        batchSql.forEach(LOGGER::info);
        return delegate.executeBatch();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return delegate.getConnection();
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return delegate.getMoreResults(current);
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return delegate.getGeneratedKeys();
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        LOGGER.info(sql);
        return delegate.executeUpdate(sql, autoGeneratedKeys);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        LOGGER.info(sql);
        return delegate.executeUpdate(sql, columnIndexes);
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        LOGGER.info(sql);
        return delegate.executeUpdate(sql, columnNames);
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        LOGGER.info(sql);
        return delegate.execute(sql, autoGeneratedKeys);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        LOGGER.info(sql);
        return delegate.execute(sql, columnIndexes);
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        LOGGER.info(sql);
        return delegate.execute(sql, columnNames);
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        return delegate.getResultSetHoldability();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return delegate.isClosed();
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        delegate.setPoolable(poolable);
    }

    @Override
    public boolean isPoolable() throws SQLException {
        return delegate.isPoolable();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        delegate.closeOnCompletion();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return delegate.isCloseOnCompletion();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return delegate.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return delegate.isWrapperFor(iface);
    }
}
