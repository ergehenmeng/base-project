package com.fanyin.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * 该类主要配置各个过期时间的缓存管理器
 * @author 二哥很猛
 * @date 2018/1/8 14:40
 */
@Configuration
@EnableCaching
@PropertySource("classpath:redis.properties")
public class RedisConfiguration{

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
     * 用于业务上进行缓存数据 获取缓存数据
     * <br>在新版本fastJson也提供了序列化:
     * <br>FastJsonRedisSerializer(可配置化)
     * <br>GenericFastJsonRedisSerializer(默认配置)
     * @param connectionFactory 由{@link org.springframework.boot.autoconfigure.data.redis.LettuceConnectionConfiguration}生成
     * @return bean
     */
    @Bean("redisTemplate")
    @Primary
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * 默认缓存管理期 默认30分钟过期
     * @return bean
     */
    @Bean("longCacheManager")
    public CacheManager longCacheManager(RedisConnectionFactory connectionFactory){
        return this.getCacheManager(connectionFactory,longExpire);
    }

    /**
     * 值序列号方式
     * @return jackson
     */
    private RedisSerializer<Object> valueSerializer(){
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    /**
     * 获取缓存管理器
     * @param expire 过期时间 默认单位 秒
     * @return CacheManager
     */
    private CacheManager getCacheManager(RedisConnectionFactory connectionFactory,int expire){
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
     * @return bean
     */
    @Bean("cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory){
        return this.getCacheManager(connectionFactory,0);
    }

    /**
     * 10分钟过期的缓存管理器
     * @return bean
     */
    @Bean("shortCacheManager")
    public CacheManager smallCacheManager(RedisConnectionFactory connectionFactory){
        return this.getCacheManager(connectionFactory,smallExpire);
    }

    /**
     * 1分钟过期的缓存管理器
     * @return bean
     */
    @Bean("smallCacheManager")
    public CacheManager shortCacheManager(RedisConnectionFactory connectionFactory){
        return this.getCacheManager(connectionFactory,shortExpire);
    }
}
