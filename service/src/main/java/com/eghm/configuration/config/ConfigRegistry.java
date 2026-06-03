package com.eghm.configuration.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统参数配置特色处理
 *
 * @author 二哥很猛
 * @since 2025/7/22
 */
@Configuration
public class ConfigRegistry {

    private static final Map<String, ConfigHandler> REGISTER_MAP = new ConcurrentHashMap<>(8);

    ConfigRegistry(ObjectProvider<List<ConfigHandler>> provider) {
        provider.ifAvailable(registers -> {
            for (ConfigHandler register : registers) {
                REGISTER_MAP.put(register.getName(), register);
            }
        });
    }

    /**
     * 处理配置
     *
     * @param name name
     * @param value value
     */
    public static void handle(String name, String value) {
        ConfigHandler register = REGISTER_MAP.get(name);
        if (register != null) {
            register.handle(value);
        }
    }
}
