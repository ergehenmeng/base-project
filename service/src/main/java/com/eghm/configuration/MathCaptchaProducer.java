package com.eghm.configuration;


import com.eghm.constants.CommonConstant;
import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiFunction;

/**
 * 参考RuYi, 采用数学验证码
 *
 * @author 二哥很猛
 * @since 2024/1/10
 */
public class MathCaptchaProducer extends DefaultTextCreator {

    private static final int MAX = 50;

    private static final int MIN = 10;

    private static final Map<Integer, BiFunction<Integer, Integer, String>> MATH_MAP = new HashMap<>(4);

    static {
        MATH_MAP.put(0, (x, y) -> x + " + " + y + " = " + CommonConstant.SPECIAL_SPLIT + (x + y));
        MATH_MAP.put(1, (x, y) -> x + " - " + y + " = " + CommonConstant.SPECIAL_SPLIT + (x - y));
        MATH_MAP.put(2, (x, y) -> x + " x " + y + " = " + CommonConstant.SPECIAL_SPLIT + (x * y));
    }

    @Override
    public String getText() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x = random.nextInt(MAX);
        int y = random.nextInt(MIN);
        int symbol = random.nextInt(MATH_MAP.size());
        return MATH_MAP.get(symbol).apply(x, y);
    }

}
