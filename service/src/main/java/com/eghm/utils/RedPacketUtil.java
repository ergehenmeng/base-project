package com.eghm.utils;

import com.eghm.common.enums.ErrorCode;
import com.eghm.common.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author wyb
 * @date 2023/3/28 11:05
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedPacketUtil {

    /**
     * 红包最小金额
     */
    private static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(0.01D);

    /**
     * 生成红包
     * @param amount 总金额
     * @param num 人数
     * @return 红包金额列表
     */
    public static List<BigDecimal> generate(BigDecimal amount, int num) {
        BigDecimal minAmount = MIN_AMOUNT.multiply(BigDecimal.valueOf(num));
        if (amount.compareTo(minAmount) < 0) {
            log.error("红包金额不满足每人最小[{}]元 [{}] [{}]", MIN_AMOUNT, amount, num);
            throw new BusinessException(ErrorCode.RED_PACKET_ERROR);
        }
        // 只能满足每人最小的金额
        if (amount.compareTo(minAmount) == 0) {
            return generateMin(num);
        }
        // 大于最小金额
        return generateRandom(amount, num);
    }

    /**
     * 随机发放红包金额
     * @param amount 总金额
     * @param num 人数
     * @return 金额
     */
    private static List<BigDecimal> generateRandom(BigDecimal amount, int num) {
        List<BigDecimal> result = Lists.newArrayListWithExpectedSize(num);
        BigDecimal total = amount;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < num; i++) {
            if (i == num - 1) {
                result.add(total);
                break;
            }
            BigDecimal endRange = total.multiply(BigDecimal.valueOf(2L)).divide(BigDecimal.valueOf((long) num - i), 2, RoundingMode.HALF_DOWN);
            BigDecimal value = BigDecimal.valueOf(random.nextDouble(MIN_AMOUNT.doubleValue(), endRange.doubleValue())).setScale(2, RoundingMode.DOWN);
            result.add(value);
            total = total.subtract(value);
        }
        // 打乱顺序
        Collections.shuffle(result);
        return result;
    }

    /**
     * 每人发放只能是最小金额
     * @param num 数量
     * @return 金额
     */
    private static List<BigDecimal> generateMin(int num) {
        List<BigDecimal> result = Lists.newArrayListWithExpectedSize(num);
        for (int i = 0; i < num; i++) {
            result.add(MIN_AMOUNT);
        }
        return result;
    }

}
