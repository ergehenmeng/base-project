package com.eghm.common.impl;

import com.eghm.common.JsonService;
import com.eghm.enums.ErrorCode;
import com.eghm.exception.ParameterException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.eghm.utils.StringUtil.isBlank;

/**
 * @author 殿小二
 * @since 2020/8/28
 */
@Service("jsonService")
@Slf4j
@AllArgsConstructor
public class JsonServiceImpl implements JsonService {

    private final ObjectMapper objectMapper;

    @Override
    public String toJson(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("json序列化异常 o:[{}]", o, e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    @Override
    public <T> T fromJson(String json, Class<T> cls) {
        if (isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            log.error("json反序列化异常 json:[{}] cls:[{}]", json, cls, e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    @Override
    public <T> T fromJson(String json, TypeReference<T> reference) {
        if (isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, reference);
        } catch (JsonProcessingException e) {
            log.error("json反序列化异常 json:[{}] type:[{}]", json, reference.getType(), e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    @Override
    public <T> List<T> fromJsonList(String json, Class<T> cls) {
        if (isBlank(json)) {
            return Lists.newArrayList();
        }
        try {
            return objectMapper.readValue(json, this.parseListJavaType(cls));
        } catch (JsonProcessingException e) {
            log.error("json反序列化异常 json:[{}] type:[{}]", json, cls, e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    /**
     * 生成list泛型对象
     *
     * @param elementType <T>对象类型
     * @return javaType
     */
    private JavaType parseListJavaType(Class<?>... elementType) {
        return objectMapper.getTypeFactory().constructParametricType(List.class, elementType);
    }
}
