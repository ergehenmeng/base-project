package com.eghm.service.business.impl;

import com.eghm.mapper.LotteryPrizeMapper;
import com.eghm.service.business.LotteryPrizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 奖品信息表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-03-27
 */
@Service
@AllArgsConstructor
public class LotteryPrizeServiceImpl implements LotteryPrizeService {

    private final LotteryPrizeMapper lotteryPrizeMapper;
}
