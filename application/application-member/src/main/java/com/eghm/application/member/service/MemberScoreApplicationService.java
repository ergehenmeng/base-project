package com.eghm.application.member.service;

import com.eghm.application.shared.cache.CacheService;
import com.eghm.application.shared.vo.business.member.SignInVO;
import com.eghm.constants.CacheConstant;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.member.model.MemberScoreLog;
import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.ScoreType;
import com.eghm.domain.shared.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

/**
 * 会员积分服务 - 负责积分、签到相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberScoreApplicationService {
    
    private final CacheService cacheService;
    private final MemberRepository memberRepository;
    private final MemberScoreLogApplicationService memberScoreLogService;
    
    public void signIn(Long memberId) {
        Member member = memberRepository.findById(memberId);
        long day = ChronoUnit.DAYS.between(member.getCreateTime().toLocalDate(), LocalDate.now());
        String signKey = CacheConstant.MEMBER_SIGN_IN + memberId;
        boolean signIn = cacheService.getBitmap(signKey, day);
        if (signIn) {
            log.warn("用户重复签到 memberId:[{}]", memberId);
            return;
        }
        int score = memberScoreLogService.getSignInScore();
        this.updateScore(memberId, ScoreType.SIGN_IN, score, null);
        cacheService.setBitmap(signKey, day, true);
    }
    
    public SignInVO getSignIn(Long memberId) {
        Member member = memberRepository.findById(memberId);
        LocalDate registerDate = member.getCreateTime().toLocalDate();
        long registerDays = ChronoUnit.DAYS.between(registerDate, LocalDate.now());
        String signKey = CacheConstant.MEMBER_SIGN_IN + memberId;
        boolean todaySign = cacheService.getBitmap(signKey, registerDays);
        List<Boolean> monthList = this.getMonthSign(signKey, registerDate);
        SignInVO vo = new SignInVO();
        vo.setMonthSign(monthList);
        vo.setTodaySign(todaySign);
        return vo;
    }
    
    public void updateScore(Long memberId, ScoreType scoreType, Integer score, String remark) {
        if (score == 0) {
            log.info("积分变动为零,不做任何处理 [{}] [{}]", memberId, score);
            return;
        }
        Member member = memberRepository.findById(memberId);
        MemberScoreLog scoreLog = member.changeScore(scoreType, score, remark);
        int updated = memberRepository.increaseScore(memberId, scoreLog.getScore());
        if (updated != 1) {
            log.error("更新会员积分失败 [{}] [{}] [{}]", memberId, scoreType, scoreLog.getScore());
            throw new BusinessException(ErrorCode.SCORE_UPDATE_ERROR);
        }
        memberScoreLogService.insert(scoreLog);
    }
    
    private List<Boolean> getMonthSign(String signKey, LocalDate registerDate) {
        LocalDate startDay = LocalDate.now().withDayOfMonth(1);
        long offset = ChronoUnit.DAYS.between(registerDate, startDay);
        Long bitmap64 = cacheService.getBitmapOffset(signKey, offset, LocalDate.now().getMonth().maxLength());
        int monthDays = startDay.lengthOfMonth();
        List<Boolean> monthList = Lists.newArrayListWithCapacity(31);
        int bitmap = 1;
        for (int i = 0; i < monthDays; i++) {
            monthList.add((bitmap64 & bitmap) == bitmap);
            bitmap64 >>= 1;
        }
        Collections.reverse(monthList);
        return monthList;
    }
}
