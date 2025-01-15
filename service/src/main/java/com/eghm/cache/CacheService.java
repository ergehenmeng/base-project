package com.eghm.cache;

import com.eghm.constants.CommonConstant;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


/**
 * @author 二哥很猛
 * @since 2018/11/21 16:19
 */
public interface CacheService {

    /**
     * 根据key 获取缓存信息,
     *
     * @param key           缓存key
     * @param typeReference 缓存结果泛型类型
     * @param supplier      如果缓存为空时,通过该回调方法进行查询数据库等
     * @param <T>           结果类型
     * @return 缓存结果
     */
    <T> T getValue(String key, TypeReference<T> typeReference, Supplier<T> supplier);

    /**
     * 缓存对象
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间 单位:秒
     */
    void setValue(String key, Object value, long expire);

    /**
     * 缓存对象
     *
     * @param key    key
     * @param value  value
     * @param expire 过期时间
     * @param unit   单位时间
     */
    void setValue(String key, Object value, long expire, TimeUnit unit);

    /**
     * 是否存在指定key的缓存
     *
     * @param key key
     * @return true:存在 false:不存在
     */
    boolean exist(String key);

    /**
     * 获取缓存的信息
     *
     * @param key key
     * @return 缓存信息
     */
    String getValue(String key);

    /**
     * 获取缓存信息
     *
     * @param key 缓存key
     * @param cls 缓存信息类型
     * @param <T> 普通数据类型
     * @return 缓存结果对象
     */
    <T> T getValue(String key, Class<T> cls);

    /**
     * 获取缓存信息
     *
     * @param key  缓存key
     * @param type 返回值为泛型时的定义类型
     * @param <T>  泛型结果
     * @return 缓存结果对象
     */
    <T> T getValue(String key, TypeReference<T> type);

    /**
     * 获取key的过期时间
     *
     * @param key key
     * @return 剩余过期时间
     */
    long getExpire(String key);

    /**
     * 清除缓存
     *
     * @param key key
     */
    void delete(String key);

    /**
     * 用来限制单位时间内的访问次数(一般用来限制单位时间的短信发送次数或者登录次数),注意调用该方法一次如果返回false默认访问次数会+1
     * 例如 发送短信 maxLimit=3, maxTtl=3600,表示在一个小时内最多发送3条短信
     * 表示在{maxTtl}时间内最多能访问{maxLimit}次
     *
     * @param key      key
     * @param maxLimit 次数限制
     * @param maxTtl   单位:毫秒 不能大于7天 {@link CommonConstant#LIMIT_MAX_EXPIRE}
     * @return true:限制() false:不限制(表示没有达到最大值 可以执行后续操作)
     */
    boolean limit(String key, int maxLimit, long maxTtl);

    /**
     * 设置hash值
     *
     * @param key    hashKey
     * @param hKey   key
     * @param hValue value
     */
    void setHashValue(String key, String hKey, String hValue);

    /**
     * 设置hash值
     *
     * @param key    key
     * @param expire 过期时间 毫秒
     * @param hKey   hKey
     * @param hValue hValue
     */
    void setHashValue(String key, long expire, String hKey, String hValue);

    /**
     * 获取hash中的值
     *
     * @param key  key
     * @param hKey hashKey
     * @return hValue
     */
    String getHashValue(String key, String hKey);

    /**
     * 获取hash中的值
     *
     * @param key  key
     * @param hKey hashKey
     * @param type 指定类型
     * @return hValue
     * @param <T> T
     */
    <T> T getHashValue(String key, String hKey, Class<T> type);

    /**
     * 获取hash中的值
     *
     * @param key  key
     * @param hKey hashKey
     * @return hValue
     */
    boolean hasHashKey(String key, String hKey);

    /**
     * 判断在指定key上是否有value
     *
     * @param key   key
     * @param value value
     * @return boolean
     */
    boolean hasSetKey(String key, Object value);

    /**
     * 删除hash中的某个key
     *
     * @param key   主key
     * @param hKeys hKeys
     */
    void deleteHashKey(String key, Object... hKeys);

    /**
     * 设置set值
     *
     * @param key    key
     * @param values setValue
     */
    void setSetValue(String key, String... values);

    /**
     * 获取bitmap指定位置下标的值
     *
     * @param key key
     * @param ops ops
     * @return boolean
     */
    boolean getBitmap(String key, Long ops);

}

