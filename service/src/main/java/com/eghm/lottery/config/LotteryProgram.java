package com.eghm.lottery.config;

import com.eghm.enums.ErrorCode;
import com.eghm.exception.BusinessException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 殿小二
 * @date 2021/2/19
 */
public class LotteryProgram {

    private List<PrizeOption> options;

    private Integer weights;

    private LotteryProgram(List<PrizeOption> options) {
        this.options = options;
        this.weights = options.stream().mapToInt(PrizeOption::getWeight).sum();
    }

    public static LotteryProgram createConfig(List<PrizeOption> options) {
        return new LotteryProgram(options);
    }


    public String lottery() {
        PrizeOption option = this.getPrize();
        LotteryHandler handler = option.getHandler();
        if (handler == null) {
            return null;
        }
        handler.execute(option.getName());
        return option.getName();
    }

    /**
     * 获取中奖选项
     * weight 1:2:3:4
     * 0    0.1    0.3     0.6        1.0
     * | -- | ---- | ------ | -------- |
     */
    private PrizeOption getPrize() {
        int start = 0;
        int end = 0;
        PrizeOption option = null;
        int random = ThreadLocalRandom.current().nextInt(weights);
        for (int i = 0; i < options.size(); i++) {
            PrizeOption index = options.get(i);
            end += index.getWeight();
            if (i != 0) {
                start += options.get(i - 1).getWeight();
            }
            if (start <= random && random < end) {
                option = index;
                break;
            }
        }
        if (option == null) {
            throw new BusinessException(ErrorCode.LOTTERY_ERROR);
        }
        return option;
    }

    public static void main(String[] args) {
        List<PrizeOption> optionList = new ArrayList<>();
        optionList.add(new PrizeOption() {
            @Override
            public int getWeight() {
                return 1;
            }

            @Override
            public String getName() {
                return "百分之10";
            }

            @Override
            public LotteryHandler getHandler() {
                return System.out::println;
            }
        });

        optionList.add(new PrizeOption() {
            @Override
            public int getWeight() {
                return 2;
            }

            @Override
            public String getName() {
                return "百分之20";
            }

            @Override
            public LotteryHandler getHandler() {
                return System.out::println;
            }
        });

        optionList.add(new PrizeOption() {
            @Override
            public int getWeight() {
                return 3;
            }

            @Override
            public String getName() {
                return "百分之30";
            }

            @Override
            public LotteryHandler getHandler() {
                return System.out::println;
            }
        });

        optionList.add(new PrizeOption() {
            @Override
            public int getWeight() {
                return 4;
            }

            @Override
            public String getName() {
                return "百分之40";
            }

            @Override
            public LotteryHandler getHandler() {
                return System.out::println;
            }
        });
        LotteryProgram program = LotteryProgram.createConfig(optionList);
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String lottery = program.lottery();
            map.compute(lottery, (s, integer) -> {
                if (integer == null) {
                    return 1;
                }
                return integer + 1;
            });
        }
        map.forEach((s, integer) -> {
            System.out.println(s + " : "  + integer);
        });
    }
}
