package com.eghm.configuration;

import com.eghm.common.constant.CommonConstant;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 该类主要配置各个过期时间的缓存管理器
 *
 * @author 二哥很猛
 * @date 2018/1/8 14:40
 */
@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
public class RedisConfiguration {

    /**
     * 长过期时间 默认30分钟
     */
    @Value("${long-expire:3600}")
    private int longExpire;

    /**
     * 短过期时间 默认10分钟
     */
    @Value("${short-expire:600}")
    private int shortExpire;

    /**
     * 超短时间过期 1分钟
     */
    @Value("${small-expire:60}")
    private int smallExpire;

    @Autowired
    private Gson gson;

    /**
     * 默认缓存管理期 默认30分钟过期
     *
     * @return bean
     */
    @Bean("longCacheManager")
    public CacheManager longCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, longExpire);
    }

    /**
     * 值序列号方式
     *
     * @return jackson
     */
    private RedisSerializer<Object> valueSerializer() {
        return new RedisSerializer<Object>() {
            @Override
            public byte[] serialize(Object o) throws SerializationException {
                return gson.toJson(o).getBytes(CommonConstant.CHARSET);
            }

            @Override
            public Object deserialize(byte[] bytes) throws SerializationException {
                return gson.fromJson(new String(bytes,CommonConstant.CHARSET), Object.class);
            }
        };
    }

    /**
     * 获取缓存管理器
     *
     * @param expire 过期时间 默认单位 秒
     * @return CacheManager
     */
    private CacheManager getCacheManager(RedisConnectionFactory connectionFactory, int expire) {
        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(
                        RedisCacheConfiguration
                                .defaultCacheConfig()
                                .entryTtl(Duration.ofSeconds(expire))
                                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                ).build();
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
    public CacheManager smallCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, smallExpire);
    }

    /**
     * 1分钟过期的缓存管理器
     *
     * @return bean
     */
    @Bean("smallCacheManager")
    public CacheManager shortCacheManager(RedisConnectionFactory connectionFactory) {
        return this.getCacheManager(connectionFactory, shortExpire);
    }
}
