package com.eghm.configuration;

import com.eghm.cache.CacheService;
import com.eghm.common.UserTokenService;
import com.eghm.common.JsonService;
import com.eghm.common.impl.JwtUserTokenServiceImpl;
import com.eghm.common.impl.RedisUserTokenServiceImpl;
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
    public UserTokenService jwtAccessTokenService(SystemProperties systemProperties) {
        return new JwtUserTokenServiceImpl(systemProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "system.manage.token", name = "token-type", havingValue = "redis", matchIfMissing = true)
    public UserTokenService redisAccessTokenService(SystemProperties systemProperties, CacheService cacheService, JsonService jsonService) {
        return new RedisUserTokenServiceImpl(systemProperties, cacheService, jsonService);
    }
}
