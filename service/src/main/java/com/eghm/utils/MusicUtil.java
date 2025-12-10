package com.eghm.utils;

import cn.hutool.core.util.NumberUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author 二哥很猛
 * @since 2025/12/5
 */
@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class MusicUtil {

    private static final Map<Integer, String> NUMBER_MAP = Map.of(1, "do", 2, "re", 3, "mi", 4, "fa", 5, "so", 6, "la", 7, "xi");

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        log.info("请输入小节简谱长度:");
        Scanner sc = new Scanner(System.in);
        StringBuilder builder = new StringBuilder();
        int length = 0;
        while (true) {
            boolean nextLine = sc.hasNextLine();
            if (nextLine) {
                String next = sc.nextLine();
                if (next.equals("q")) {
                    break;
                }
                boolean b = NumberUtil.isInteger(next);
                if (b) {
                    int nexted = Integer.parseInt(next);
                    if (nexted < 4 || nexted > 32) {
                        log.error("简谱长度应在4~32位,请重新输入:");
                        continue;
                    }
                    length = nexted;
                    generate(builder, nexted);
                } else if (StringUtil.isBlank(next) && length > 4) {
                    generate(builder, length);
                }
            }
        }
    }

    private static void generate(StringBuilder builder, int length) {
        builder.delete(0, builder.length());
        start(builder);
        List<Integer> numberList = new ArrayList<>(32);
        for (int i = 0; i < length; i++) {
            int number = new SecureRandom().nextInt(7) + 1;
            builder.append(number).append("   ");
            numberList.add(number);
        }
        end(builder);
        builder.append("\r\n");
        start(builder);
        for (Integer i : numberList) {
            builder.append(NUMBER_MAP.get(i)).append("  ");
        }
        end(builder);
        cutLine(builder, length * 4 + 3);
        System.out.println(builder);
    }

    private static void start(StringBuilder builder) {
        builder.append("▍  ");
    }

    private static void end(StringBuilder builder) {
        builder.append("▍");
    }

    private static void cutLine(StringBuilder builder, int num) {
        builder.append("\r\n");
        builder.append("-".repeat(num));
    }
}
