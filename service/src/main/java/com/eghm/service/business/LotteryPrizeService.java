package com.eghm.service.business;

import com.eghm.model.LotteryPrize;
import com.eghm.model.dto.business.lottery.LotteryPrizeRequest;

import java.util.List;

/**
 * <p>
 * 奖品信息表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryPrizeService {
    
    /**
     * 添加抽奖活动奖品信息
     * @param lotteryId 活动id
     * @param prizeList 奖品信息
     * @return 奖品id
     */
    List<LotteryPrize> insert(Long lotteryId, List<LotteryPrizeRequest> prizeList);

    /**
     * 更新抽奖活动奖品信息
     * @param lotteryId 活动id
     * @param prizeList 奖品信息
     * @return 奖品id
     */
    List<LotteryPrize> update(Long lotteryId, List<LotteryPrizeRequest> prizeList);
}
