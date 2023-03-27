package com.eghm.service.business.impl;

import com.eghm.mapper.LotteryPrizePositionMapper;
import com.eghm.service.business.LotteryPrizePositionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 抽奖位置配置表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
public class LotteryPrizePositionServiceImpl implements LotteryPrizePositionService {

    private final LotteryPrizePositionMapper lotteryPrizePositionMapper;
}
