package com.eghm.platform.iam.config;

import com.eghm.foundation.core.configuration.ApplicationProperties;
import com.eghm.foundation.cache.service.CacheService;
import com.eghm.platform.config.service.CommonService;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.platform.iam.service.UserTokenService;
import com.eghm.platform.iam.service.impl.JwtUserTokenServiceImpl;
import com.eghm.platform.iam.service.impl.RedisUserTokenServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * token配置 (管理后台)
 *
 * @author 二哥很猛
 * @since 2023/7/14
 */

@Configuration
public class TokenConfig {

    @Bean
    @ConditionalOnProperty(prefix = "application.manage.token", name = "token-type", havingValue = "jwt")
    public UserTokenService jwtAccessTokenService(CommonService commonService, ApplicationProperties applicationProperties) {
        return new JwtUserTokenServiceImpl(commonService, applicationProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "application.manage.token", name = "token-type", havingValue = "redis", matchIfMissing = true)
    public UserTokenService redisAccessTokenService(ApplicationProperties applicationProperties, CommonService commonService, CacheService cacheService, JsonService jsonService) {
        return new RedisUserTokenServiceImpl(jsonService, cacheService, commonService, applicationProperties);
    }
}
