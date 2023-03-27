package com.eghm.service.business.impl;

import com.eghm.mapper.LotteryMapper;
import com.eghm.service.business.LotteryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 抽奖活动表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final LotteryMapper lotteryMapper;
}
