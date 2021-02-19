package com.eghm.lottery.config;

/**
 * @author 殿小二
 * @date 2021/2/19
 */
public interface PrizeOption {

    /**
     * 奖品中奖概率值
     * @return 中奖概率
     */
    int getWeight();

    /**
     * 奖品名称 必须唯一
     * @return name
     */
    String getName();

    /**
     * 中奖时的处理逻辑
     * @return 逻辑
     */
    LotteryHandler getHandler();

}
