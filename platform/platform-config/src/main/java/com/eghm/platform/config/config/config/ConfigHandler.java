package com.eghm.platform.config.config.config;

/**
 * @author 二哥很猛
 * @since 2025/7/22
 */
public interface ConfigHandler {

    /**
     * 获取注册nid名称
     * @return nid
     */
    String getName();

    /**
     * 处理配置信息
     *
     * @param value value
     */
    void handle(String value);
}
