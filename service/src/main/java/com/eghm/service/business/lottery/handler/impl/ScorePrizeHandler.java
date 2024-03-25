package com.eghm.service.business.lottery.handler.impl;

import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.ScoreType;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.PrizeType;
import com.eghm.exception.BusinessException;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.model.LotteryPrize;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.service.business.lottery.LotteryPrizeService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.eghm.cache.CacheProxyService;
import com.eghm.service.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wyb
 * @since 2024/2/27
 */
@Service
@AllArgsConstructor
@Slf4j
public class ScorePrizeHandler implements PrizeHandler {

    private final MemberService memberService;

    private final CacheProxyService cacheProxyService;

    private final ScoreAccountService scoreAccountService;

    private final LotteryPrizeService lotteryPrizeService;

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.SCORE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("抽中积分啦 [{}] [{}]", memberId, lottery);
        LotteryPrize prize = cacheProxyService.getPrizeById(config.getPrizeId());
        if (prize == null) {
            log.error("积分中奖奖品不存在 [{}]", config.getPrizeId());
            throw new BusinessException(ErrorCode.SCORE_PRIZE_NULL);
        }
        lotteryPrizeService.accumulationLotteryNum(prize.getId());
        ScoreAccountDTO dto = new ScoreAccountDTO();
        dto.setMerchantId(config.getMerchantId());
        dto.setAmount(prize.getNum());
        dto.setChargeType(ChargeType.DRAW);
        scoreAccountService.updateAccount(dto);
        memberService.updateScore(memberId, ScoreType.LOTTERY, prize.getNum());
    }
}
