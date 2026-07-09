package com.eghm.infrastructure.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Nonnull;
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
    
    private static final RedisSerializer<Object> VALUE_SERIALIZER;

    static {
        // 值序列化方式,此处已经采用jackson序列化,因为jackson可以根据缓存中json中的附加信息生成相应类(尤其是泛型对象),gson只能手动指定
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder().allowIfBaseType("com.eghm.").allowIfBaseType("java.lang.").allowIfBaseType("java.util.").build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        VALUE_SERIALIZER = new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }
    
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
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(VALUE_SERIALIZER))
        ).build();
    }

    @Override
    public KeyGenerator keyGenerator() {

        return new SimpleKeyGenerator() {

            @Nonnull
            @Override
            public Object generate(@Nonnull Object target, @Nonnull Method method, @Nonnull Object... params) {
                if (params.length == 0) {
                    return "all";
                }
                return super.generate(target, method, params);
            }
        };
    }

}
