package com.eghm.application.shared.common;

/**
 * 系统配置读取服务.
 *
 * @author 二哥很猛
 * @since 2026/07/09
 */
public interface SysConfigService {

    /**
     * 根据nid获取系统参数配置值.
     *
     * @param nid 唯一nid
     * @return 系统参数配置值
     */
    String getString(String nid);

    /**
     * 根据nid获取boolean类型系统参数配置值.
     *
     * @param nid 唯一nid
     * @return 系统参数配置值
     */
    boolean getBoolean(String nid);

    /**
     * 根据nid获取int类型系统参数配置值.
     *
     * @param nid 唯一nid
     * @return 系统参数配置值
     */
    int getInt(String nid);

    /**
     * 根据nid获取int类型系统参数配置值.
     *
     * @param nid 唯一nid
     * @param defaultValue 默认值
     * @return 系统参数配置值
     */
    int getInt(String nid, int defaultValue);

    /**
     * 根据nid获取long类型系统参数配置值.
     *
     * @param nid 唯一nid
     * @return 系统参数配置值
     */
    long getLong(String nid);

    /**
     * 根据nid获取long类型系统参数配置值.
     *
     * @param nid 唯一nid
     * @param defaultValue 默认值
     * @return 系统参数配置值
     */
    long getLong(String nid, long defaultValue);
}
