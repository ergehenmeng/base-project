package com.eghm.configuration;


import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.security.SecureRandom;

/**
 * 重写Random随机数
 *
 * @author 二哥很猛
 * @since 2019/7/10 17:32
 */
public class TextCaptchaProducer extends DefaultTextCreator {

    @Override
    public String getText() {
        int length = getConfig().getTextProducerCharLength();
        char[] chars = getConfig().getTextProducerCharString();
        StringBuilder text = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            text.append(chars[random.nextInt(chars.length)]);
        }
        return text.toString();
    }
}
