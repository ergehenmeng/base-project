package com.eghm.service.business;

import com.eghm.model.dto.business.lottery.LotteryAddRequest;

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
}
