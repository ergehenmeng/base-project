package com.eghm.convertor;

import cn.hutool.extra.spring.SpringUtil;
import com.eghm.common.CommonService;
import com.eghm.constants.CommonConstant;
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
public class RsaPasswordDeserializer extends StdScalarDeserializer<String> {

    protected RsaPasswordDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String text = p.getText().trim();
        if (StringUtil.isBlank(text)) {
            return null;
        }
        String password;
        try {
            password = SpringUtil.getBean(CommonService.class).rsaDecrypt(text);
        } catch (Exception e) {
            log.error("RSA解密密码异常 [{}]", text, e);
            throw new BusinessException(PWD_DECODE_ERROR);
        }
        String[] split = password.split("\\|");
        if  (split.length != 2) {
            throw new BusinessException(PWD_DECODE_ERROR);
        }
        long timestamp = Long.parseLong(split[1]);
        long interval = Math.abs(System.currentTimeMillis() - Long.parseLong(split[1]));
        if (interval > CommonConstant.MAX_SYSTEM_TIME_DIFF) {
            log.warn("RSA解密密码成功,但已过有效期 [{}]", timestamp);
            throw new BusinessException(PWD_DECODE_ERROR);
        }
        return split[0];
    }
}
