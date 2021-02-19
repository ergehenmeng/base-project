package com.eghm.lottery.config;

/**
 * @author 殿小二
 * @date 2021/2/19
 */
public interface LotteryHandler {

    /**
     * 中奖处理逻辑
     * @param prizeName 奖品名称
     */
    void execute(String prizeName);

}
