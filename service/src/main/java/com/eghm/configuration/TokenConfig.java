package com.eghm.configuration;

import com.eghm.cache.CacheService;
import com.eghm.common.FileService;
import com.eghm.common.JsonService;
import com.eghm.common.impl.JwtAccessTokenServiceImpl;
import com.eghm.common.impl.RedisAccessTokenServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 二哥很猛
 * @since 2023/7/14
 */

@Configuration
public class TokenConfig {

    @Bean
    @ConditionalOnProperty(prefix = "system.manage.token", name = "token-type", havingValue = "jwt")
    public FileService.AccessTokenService jwtAccessTokenService(SystemProperties systemProperties) {
        return new JwtAccessTokenServiceImpl(systemProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.manage.token", name = "token-type", havingValue = "redis", matchIfMissing = true)
    public FileService.AccessTokenService redisAccessTokenService(SystemProperties systemProperties, CacheService cacheService, JsonService jsonService) {
        return new RedisAccessTokenServiceImpl(systemProperties, cacheService, jsonService);
    }
}
