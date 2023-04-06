package com.eghm.service.business;

import com.eghm.dto.business.lottery.LotteryConfigRequest;
import com.eghm.model.LotteryConfig;

import java.util.List;

/**
 * <p>
 * 抽奖位置配置表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryConfigService {
    
    /**
     * 新增中奖配置表
     * @param lotteryId 活动id
     * @param positionList 中奖位置概率配置信息
     * @param prizeIds 奖品id
     */
    void insert(Long lotteryId, List<LotteryConfigRequest> positionList, List<Long> prizeIds);

    /**
     * 更新中奖配置表
     * @param lotteryId 活动id
     * @param positionList 中奖位置概率配置信息
     * @param prizeIds 奖品id
     */
    void update(Long lotteryId, List<LotteryConfigRequest> positionList, List<Long> prizeIds);

    /**
     * 查询抽奖配置信息
     * @param lotteryId 活动id
     * @return 中奖配置信息
     */
    List<LotteryConfig> getList(Long lotteryId);
}
