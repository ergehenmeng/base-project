package com.eghm.configuration.sql;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 用于打印sql 仅支持mysql数据库 参考 https://code.google.com/p/log4jdbc/
 *
 * @author 二哥很猛
 * @date 2019/10/16 17:27
 */
public class LogDriver implements Driver {

    private static final String LOG_PREFIX = "log.jdbc:";

    private Driver realDriver;

    static {
        try {
            DriverManager.registerDriver(new LogDriver());
        } catch (Exception e) {
            throw new RuntimeException("无法注册LogDriver驱动程序", e);
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new RuntimeException("实例化mysql驱动失败", e);
        }
    }


    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        Driver driver = this.getDriver(url);
        this.realDriver = driver;
        Connection connect = driver.connect(this.getUrl(url), info);
        if (connect == null) {
            throw new RuntimeException("数据库驱动链接异常 " + url);
        }
        return new LogConnection(connect, new MysqlDataResolve());
    }

    private Driver getDriver(String url) throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        if (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (!driver.acceptsURL(this.getUrl(url))) {
                throw new RuntimeException("数据库url配置错误 " + url);
            }
            return driver;
        }
        throw new RuntimeException("未找到数据库驱动程序");
    }

    /**
     * 获取链接数据库的地址
     *
     * @param url url
     * @return url
     */
    private String getUrl(String url) {
        if (url.startsWith(LOG_PREFIX)) {
            return url.substring(4);
        }
        return url;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        this.realDriver = this.getDriver(url);
        return true;
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        Driver driver = getDriver(url);
        this.realDriver = driver;
        return driver.getPropertyInfo(url, info);
    }

    @Override
    public int getMajorVersion() {
        if (realDriver == null) {
            return 0;
        }
        return realDriver.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        if (realDriver == null) {
            return 0;
        }
        return realDriver.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
        return realDriver != null && realDriver.jdbcCompliant();
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException();
    }
}
