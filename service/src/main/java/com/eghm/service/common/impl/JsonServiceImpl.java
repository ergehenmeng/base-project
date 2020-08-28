package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.ParameterException;
import com.eghm.service.common.JsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@Service("jsonService")
@Slf4j
public class JsonServiceImpl implements JsonService {

    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
        if (StrUtil.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            log.error("json反序列化异常 json:[{}] cls:[{}]", cls, e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }

    @Override
    public <T> T fromJson(String json, TypeReference<T> reference) {
        if (StrUtil.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, reference);
        } catch (JsonProcessingException e) {
            log.error("json反序列化异常 json:[{}] type:[{}]", reference.getType(), e);
            throw new ParameterException(ErrorCode.JSON_FORMAT_ERROR);
        }
    }
}
