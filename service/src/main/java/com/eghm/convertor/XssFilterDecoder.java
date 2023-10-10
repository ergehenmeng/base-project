package com.eghm.convertor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

/**
 * @author 二哥很猛
 * @since 2023/10/10
 */
public class XssFilterDecoder extends StdScalarDeserializer<String> {

    public XssFilterDecoder() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String text = p.getText();
        if (StrUtil.isNotBlank(text)) {
            text = HtmlUtil.escape(text);
        }
        return text;
    }

}
