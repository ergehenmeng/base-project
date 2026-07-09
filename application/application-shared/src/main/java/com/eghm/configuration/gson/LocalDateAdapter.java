package com.eghm.configuration.gson;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 由于日志记录 WebappLogAspect/WebappLogAspect 请求参数序列化时依赖gson, 所以需要自定义日期格式化适配器
 *
 * @author 二哥很猛
 * @since 2025/1/9
 */
@Slf4j
public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return LocalDate.parse(json.getAsString(), FORMATTER);
        } catch (Exception e) {
            log.error("gson反序列化localDate异常 [{}]", json.getAsString(), e);
            throw new BusinessException(ErrorCode.DATE_CASE_ERROR);
        }
    }

    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }
}
