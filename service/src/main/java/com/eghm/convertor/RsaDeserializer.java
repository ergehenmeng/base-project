package com.eghm.convertor;

import cn.hutool.extra.spring.SpringUtil;
import com.eghm.common.CommonService;
import com.eghm.exception.BusinessException;
import com.eghm.utils.StringUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.eghm.enums.ErrorCode.PWD_DECODE_ERROR;

/**
 * rsa解密
 *
 * @author 二哥很猛
 * @since 2025/7/18
 */
@Slf4j
public class RsaDeserializer extends StdScalarDeserializer<String> {

    protected RsaDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = p.getText().trim();
        if (StringUtil.isBlank(text)) {
            return null;
        }
        try {
            return SpringUtil.getBean(CommonService.class).rsaDecrypt(text);
        } catch (Exception e) {
            log.error("RSA解密异常 [{}]", text, e);
            throw new BusinessException(PWD_DECODE_ERROR);
        }
    }
}
