package com.eghm.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;

import java.lang.reflect.Method;
import java.time.Duration;

import static com.eghm.constants.CacheConstant.SEPARATOR;

/**
 * redis缓存配置, 该类主要配置各个过期时间的缓存管理器
 *
 * @author 二哥很猛
 * @since 2018/1/8 14:40
 */
@Configuration
@EnableCaching
@AllArgsConstructor
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class RedisConfig implements CachingConfigurer {

    /**
     * 默认缓存管理期 默认30分钟过期
     *
     * @return bean
     */
    @Bean("longCacheManager")
    public CacheManager longCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, 1800);
    }

    /**
     * 系统级缓存管理器 默认永不过期
     *
     * @return bean
     */
    @Bean("cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, 0);
    }

    /**
     * 10分钟过期的缓存管理器
     *
     * @return bean
     */
    @Bean("shortCacheManager")
    public CacheManager shortCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, 600);
    }

    /**
     * 1分钟过期的缓存管理器
     *
     * @return bean
     */
    @Bean("smallCacheManager")
    public CacheManager smallCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, 60);
    }

    /**
     * 获取缓存管理器
     *
     * @param expire 过期时间 默认单位 秒
     * @return CacheManager
     */
    private CacheManager getCacheManager(RedisConnectionFactory connectionFactory, int expire) {
        return RedisCacheManager.builder(connectionFactory).cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(expire))
                        .computePrefixWith(cacheName -> cacheName + SEPARATOR)
                        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
        ).build();
    }

    /**
     * 值序列化方式,此处已经采用jackson序列化,因为jackson可以根据缓存中json中的附加信息生成相应类(尤其是泛型对象),gson只能手动指定
     *
     * @return jackson
     */
    private RedisSerializer<Object> valueSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

    @Override
    public KeyGenerator keyGenerator() {

        return new SimpleKeyGenerator() {

            @NonNull
            @Override
            public Object generate(@NonNull Object target, @NonNull Method method, @NonNull Object... params) {
                if (params.length == 0) {
                    return "all";
                }
                return super.generate(target, method, params);
            }
        };
    }

}
