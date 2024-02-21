package com.eghm.service.business.lottery.handler.impl;

import com.eghm.dto.business.account.score.ScoreAccountDTO;
import com.eghm.enums.ref.ChargeType;
import com.eghm.enums.ref.PrizeType;
import com.eghm.model.Lottery;
import com.eghm.model.LotteryConfig;
import com.eghm.service.business.ScoreAccountService;
import com.eghm.service.business.lottery.handler.PrizeHandler;
import com.eghm.service.member.MemberScoreLogService;
import com.eghm.service.member.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author wyb
 * @since 2024/2/27
 */
@Service
@AllArgsConstructor
@Slf4j
public class ScorePrizeHandler implements PrizeHandler {

    private final MemberService memberService;

    private final ScoreAccountService scoreAccountService;

    @Override
    public boolean supported(PrizeType prizeType) {
        return prizeType == PrizeType.COUPON;
    }

    @Override
    public void execute(Long memberId, Lottery lottery, LotteryConfig config) {
        log.info("抽中积分啦 [{}] [{}]", memberId, lottery);
        ScoreAccountDTO dto = new ScoreAccountDTO();
        dto.setMerchantId(config.getMerchantId());
        dto.setAmount(12);
        dto.setChargeType(ChargeType.DRAW);
        scoreAccountService.updateAccount(dto);
    }
}
