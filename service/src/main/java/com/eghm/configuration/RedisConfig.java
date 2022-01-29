package com.eghm.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
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
public class RedisConfig {

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
     * 值序列化方式,此处已经采用jackson序列化,因为jackson可以根据缓存中json中的附加信息生成相应类(尤其是泛型对象),gson只能手动指定
     *
     * @return jackson
     */
    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(),ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        serializer.setObjectMapper(mapper);
        return serializer;
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
