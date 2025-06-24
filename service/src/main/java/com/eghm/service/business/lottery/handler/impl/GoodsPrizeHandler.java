package com.eghm.service.business.lottery.handler.impl;

import com.eghm.dto.ext.ThreadHolder;
import com.eghm.enums.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2025/6/7
 */
@Service
@AllArgsConstructor
@Slf4j
public class GoodsPrizeHandler implements PrizeHandler {

    private final LotteryPrizeService lotteryPrizeService;

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.GOODS;
    }

    @Override
    public boolean execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("用户[{}]在抽奖活动[{}]中得自定义商品", memberId, lottery.getId());
        lotteryPrizeService.decrement(config.getPrizeId());
        ThreadHolder.setLottery(false);
        return true;
    }
}
