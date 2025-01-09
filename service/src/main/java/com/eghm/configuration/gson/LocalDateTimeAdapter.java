package com.eghm.configuration.gson;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Slf4j
public class LocalDateTimeAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return LocalDateTime.parse(json.getAsString(), FORMATTER);
        } catch (Exception e) {
            log.error("gson反序列化localDateTime异常 [{}]", json.getAsString(), e);
            throw new BusinessException(ErrorCode.DATE_CASE_ERROR);
        }
    }

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }
}
