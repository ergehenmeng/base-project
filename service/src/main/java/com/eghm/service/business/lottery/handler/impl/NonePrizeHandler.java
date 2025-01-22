package com.eghm.service.business.lottery.handler.impl;

import com.eghm.enums.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2023/4/7
 */
@Service
@AllArgsConstructor
@Slf4j
public class NonePrizeHandler implements PrizeHandler {

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.NONE;
    }

    @Override
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("用户[{}]在抽奖活动[{}]中没有中奖", memberId, lottery.getId());
    }
}
