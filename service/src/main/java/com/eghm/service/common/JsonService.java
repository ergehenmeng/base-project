package com.eghm.service.common;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
public interface JsonService {

    /**
     * 将对象转为json字符粗
     * @param o 对象
     * @return json串
     */
    String toJson(Object o);

    /**
     * 将json转换为对象
     * @param json json字符串
     * @param cls 对象类型
     * @param <T> <T>
     * @return obj
     */
    <T> T fromJson(String json, Class<T> cls);

    /**
     * 将json转换为对象 主要处理泛型类型
     * @param json json
     * @param reference 泛型指定类型
     * @param <T> <T>
     * @return obj
     */
    <T> T fromJson(String json, TypeReference<T> reference);

    /**
     * 将json转换为对象 list格式
     * @param json json
     * @param cls cls
     * @param <T> <T>
     * @return list
     */
    <T> List<T> fromJsonList(String json, Class<T> cls);
}
