package com.eghm.configuration;

import com.eghm.service.cache.CacheService;
import com.eghm.service.common.AccessTokenService;
import com.eghm.service.common.JsonService;
import com.eghm.service.common.impl.JwtAccessTokenServiceImpl;
import com.eghm.service.common.impl.RedisAccessTokenServiceImpl;
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
    public AccessTokenService jwtAccessTokenService(SystemProperties systemProperties) {
        return new JwtAccessTokenServiceImpl(systemProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.manage.token", name = "token-type", havingValue = "redis", matchIfMissing = true)
    public AccessTokenService redisAccessTokenService(SystemProperties systemProperties, CacheService cacheService, JsonService jsonService) {
        return new RedisAccessTokenServiceImpl(systemProperties, cacheService, jsonService);
    }
}
