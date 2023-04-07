package com.eghm.service.business.lottery;

import com.eghm.dto.business.lottery.LotteryAddRequest;
import com.eghm.dto.business.lottery.LotteryEditRequest;
import com.eghm.model.Lottery;

/**
 * <p>
 * 抽奖活动表 服务类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
public interface LotteryService {
    
    /**
     * 新增抽奖活动
     * @param request 抽奖配置信息
     */
    void create(LotteryAddRequest request);

    /**
     * 更新抽奖活动
     * @param request 信息
     */
    void update(LotteryEditRequest request);

    /**
     *  抽奖
     * @param lotteryId 活动id
     * @param userId 用户id
     */
    void lottery(Long lotteryId, Long userId);

    /**
     * 查询抽奖活动
     * @param lotteryId 活动id
     * @return 抽奖信息
     */
    Lottery selectByIdRequired(Long lotteryId);
}
