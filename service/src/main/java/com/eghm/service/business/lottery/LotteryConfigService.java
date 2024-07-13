package com.eghm.service.business.lottery;

import com.eghm.dto.business.lottery.LotteryConfigRequest;
import com.eghm.model.LotteryConfig;
import com.eghm.model.LotteryPrize;

import java.util.List;
import java.util.Map;

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
     *
     * @param lotteryId    活动id
     * @param positionList 中奖位置概率配置信息
     * @param prizeMap     奖品信息
     */
    void insert(Long lotteryId, List<LotteryConfigRequest> positionList, Map<Integer, LotteryPrize> prizeMap);

    /**
     * 更新中奖配置表
     *
     * @param lotteryId    活动id
     * @param positionList 中奖位置概率配置信息
     * @param prizeMap     奖品信息
     */
    void update(Long lotteryId, List<LotteryConfigRequest> positionList, Map<Integer, LotteryPrize> prizeMap);

    /**
     * 查询抽奖配置信息
     *
     * @param lotteryId 活动id
     * @return 中奖配置信息
     */
    List<LotteryConfig> getList(Long lotteryId);

    /**
     * 删除抽奖配置信息
     *
     * @param lotteryId 活动id
     * @param merchantId 商户id
     */
    void delete(Long lotteryId, Long merchantId);
}
