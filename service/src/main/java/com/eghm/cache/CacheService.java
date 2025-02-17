package com.eghm.cache;

import com.eghm.constants.CommonConstant;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;


/**
 * @author 二哥很猛
 * @since 2018/11/21 16:19
 */
public interface CacheService {

    /**
     * 缓存对象 默认30分钟
     *
     * @param key   key
     * @param value value
     */
    void setValue(String key, Object value);

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
     * 如果没有则添加否则不添加
     *
     * @param key   key
     * @param value value
     * @return true:添加成功 false:添加失败
     */
    boolean setIfAbsent(String key, String value);

    /**
     * 缓存对象,并设置过期时间
     *
     * @param key        key
     * @param value      value
     * @param expireTime 未来某个过期的时间
     */
    void setValue(String key, Object value, Date expireTime);

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
     * @param type 转换的类型
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
     * @param key key
     * @param value value
     * @return boolean
     */
    boolean hasSetKey(String key, Object value);

    /**
     * 获取hash的长度
     *
     * @param key key
     * @return 数量
     */
    long getHashSize(String key);

    /**
     * 删除hash中的某个key
     *
     * @param key   主key
     * @param hKeys hKeys
     */
    void deleteHashKey(String key, Object... hKeys);

    /**
     * 设置bitmap值
     *
     * @param key   key
     * @param ops   下标
     * @param value 值
     */
    void setBitmap(String key, Long ops, Boolean value);

    /**
     * 设置set值
     *
     * @param key key
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

    /**
     * 判断在指定key上是否有succession个数连续为true(慎用)
     * 采用bitField实现
     *
     * @param key        key
     * @param end        当前尾节点
     * @param succession 连续天数 该值越大性能越差
     * @return 个数
     */
    boolean checkSerialBoost(String key, Long end, Integer succession);

    /**
     * 查询指定位置后long的长度
     *
     * @param key    key
     * @param offset 位置
     * @return long 默认取32位
     */
    Long getBitmapOffset(String key, Long offset);

    /**
     * 查询指定位置后long的长度
     * 例如: 000011
     * offset = 0, length = 32, 则会在该数字后不足部分补零 000011(后面补领长度: 32-6)
     * offset = 2, length = 16, 则会在该数字后不足部分补零 00011(后面补领长度: 32-5)
     * @param key    key
     * @param offset 位置
     * @param length 取多少位数字返回(不能超过63)
     * @return long 默认取32位
     */
    Long getBitmapOffset(String key, Long offset, int length);

    /**
     * 插入set
     *
     * @param key key
     * @param value value
     * @param score score
     */
    void setSet(String key, String value, double score);

    /**
     * 累计插入
     *
     * @param key key
     * @param value value
     * @param score score
     */
    void setSetIncrement(String key, String value, double score);

    /**
     * 获取set排行
     *
     * @param key key
     * @param limit 多少个
     * @return 列表
     */
    Set<ZSetOperations.TypedTuple<String>> rangeWithScore(String key, Integer limit);
}

