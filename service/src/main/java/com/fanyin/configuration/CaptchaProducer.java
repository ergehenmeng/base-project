package com.fanyin.configuration;


import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 重写Random随机数
 * @author 王艳兵
 * @date 2019/7/10 17:32
 */
public class CaptchaProducer extends DefaultTextCreator {

    @Override
    public String getText(){
        int length = getConfig().getTextProducerCharLength();
        char[] chars = getConfig().getTextProducerCharString();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++){
            text.append(chars[ThreadLocalRandom.current().nextInt(chars.length)]);
        }
        return text.toString();
    }
}
