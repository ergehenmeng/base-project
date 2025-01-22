package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

/**
 * @author wyb
 * @since 2023/3/28 11:05
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedPacketUtil {

    /**
     * 红包最小金额 单位:分
     */
    private static final int MIN_AMOUNT = 1;

    /**
     * 生成红包
     *
     * @param amount 总金额 单位:分
     * @param num    人数
     * @return 红包金额列表
     */
    public static List<Integer> generate(int amount, int num) {
        int minAmount = MIN_AMOUNT * num;
        if (amount < minAmount) {
            log.error("红包金额不满足每人最小[{}]元 [{}] [{}]", MIN_AMOUNT, amount, num);
            throw new BusinessException(ErrorCode.RED_PACKET_ERROR);
        }
        // 只能满足每人最小的金额
        if (amount == minAmount) {
            return generateMin(num);
        }
        // 大于最小金额
        return generateRandom(amount, num);
    }

    /**
     * 随机发放红包金额
     *
     * @param amount 总金额, 单位:分
     * @param num    人数
     * @return 金额
     */
    private static List<Integer> generateRandom(int amount, int num) {
        List<Integer> result = Lists.newArrayListWithExpectedSize(num);
        int surplus = amount;
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                result.add(surplus);
                break;
            }
            int endRange = surplus * 2 / (num - i);
            int value = random.nextInt(MIN_AMOUNT, endRange);
            result.add(value);
            surplus = surplus - value;
        }
        // 打乱顺序
        Collections.shuffle(result, random);
        return result;
    }

    /**
     * 每人发放只能是最小金额
     *
     * @param num 数量
     * @return 金额
     */
    private static List<Integer> generateMin(int num) {
        List<Integer> result = Lists.newArrayListWithExpectedSize(num);
        for (int i = 0; i < num; i++) {
            result.add(MIN_AMOUNT);
        }
        return result;
    }

}
