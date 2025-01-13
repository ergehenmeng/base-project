package com.eghm.utils;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 红包工具类(仿微信随机红包)
 *
 * @author wyb
 * @since 2023/3/28 11:05
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedEnvelopeUtil {

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
     * 每个红包的金额必须大于最小金额,且不能大于剩余平均金额的2倍, (因为是向下取整,因此最后一个红包是总的剩余金额,也不会为0,且不受上述规则限制)
     * @param amount 总金额, 单位:分
     * @param num    人数
     * @return 金额
     */
    private static List<Integer> generateRandom(int amount, int num) {
        List<Integer> result = Lists.newArrayListWithExpectedSize(num);
        int surplus = amount;
        ThreadLocalRandom random = ThreadLocalRandom.current();
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
        // 按需决定是否打乱顺序
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
