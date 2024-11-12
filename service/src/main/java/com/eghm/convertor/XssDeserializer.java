package com.eghm.convertor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;

import java.io.IOException;

/**
 * 针对前端传递过来的数据进行xss过滤
 *
 * @author 二哥很猛
 * @since 2023/10/10
 */
public class XssDeserializer extends StdScalarDeserializer<String> {

    public XssDeserializer() {
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
