package com.eghm.utils;

import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 二哥很猛
 * @since 2025/12/5
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MusicUtil {

    private static final Map<Integer, String> NUMBER_MAP = Map.of(1, "do", 2, "re", 3, "mi", 4, "fa", 5, "so", 6, "la", 7, "xi");

    private static void generate(StringBuilder builder, int length) {
        start(builder);
        List<Integer> numberList = new ArrayList<>(32);
        for (int i = 0; i < length; i++) {
            int number = new SecureRandom().nextInt(7) + 1;
            builder.append(number).append("  ");
            numberList.add(number);
        }
        end(builder);
        builder.append("\r\n");
        start(builder);
        for (Integer i : numberList) {
            builder.append(NUMBER_MAP.get(i)).append(" ");
        }
        end(builder);
        cutLine(builder, length * 3 + 2);
    }

    private static void start(StringBuilder builder) {
        builder.append("▍ ");
    }

    private static void end(StringBuilder builder) {
        builder.append("▍");
    }

    private static void cutLine(StringBuilder builder, int num) {
        builder.append("\r\n");
        builder.append("=".repeat(num));
    }
}
