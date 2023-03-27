package com.eghm.service.business;

import com.eghm.model.dto.business.lottery.LotteryPrizeConfigRequest;

import java.util.List;

/**
 * <p>
 * 抽奖位置配置表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryPrizeConfigService {
    
    /**
     * 查询中奖配置表
     * @param lotteryId 活动id
     * @param positionList 中奖位置概率配置信息
     * @param prizeIds 奖品id
     */
    void insert(Long lotteryId, List<LotteryPrizeConfigRequest> positionList, List<Long> prizeIds);
}
