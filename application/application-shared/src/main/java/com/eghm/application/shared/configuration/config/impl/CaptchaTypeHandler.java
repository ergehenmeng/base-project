package com.eghm.application.shared.configuration.config.impl;

import com.eghm.application.shared.configuration.config.ConfigHandler;
import com.eghm.constants.ConfigConstant;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author 二哥很猛
 * @since 2025/7/22
 */
@Slf4j
@Component
@AllArgsConstructor
public class CaptchaTypeHandler implements ConfigHandler {

    private final DefaultKaptcha kaptcha;

    @Override
    public String getName() {
        return ConfigConstant.CAPTCHA_TYPE;
    }

    @Override
    public void handle(String value) {
        Properties properties = kaptcha.getConfig().getProperties();
        try {
            Class.forName(value);
            properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_IMPL, value);
        } catch (Exception e) {
            log.warn("系统参数配置无法实时生效, 未找到验证码实现类 [{}]", value);
        }
    }
}
