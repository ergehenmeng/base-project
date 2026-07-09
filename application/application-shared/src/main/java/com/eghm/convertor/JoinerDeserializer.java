package com.eghm.convertor;

import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.exception.BusinessException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.eghm.constants.CommonConstant.COMMA;

/**
 * @author 二哥很猛
 * @since 2023/1/17
 */
@Slf4j
public class JoinerDeserializer extends JsonDeserializer<String> {

    private final String delimiter;

    public JoinerDeserializer() {
        this(COMMA);
    }

    public JoinerDeserializer(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.isArray()) {
            StringBuilder sb = new StringBuilder();
            node.forEach(jsonNode -> {
                if (!sb.isEmpty()) {
                    sb.append(delimiter);
                }
                sb.append(jsonNode.asText());
            });
            return sb.toString();
        } else {
            log.error("jackson array格式转换异常 [{}]", node.getNodeType().name());
            throw new BusinessException(ErrorCode.SPLIT_ERROR);
        }
    }
}
