package com.eghm.application.member.service;

import com.eghm.domain.shared.enums.ScoreType;
import com.eghm.vo.business.member.SignInVO;

/**
 * 会员积分服务 - 负责积分、签到相关操作
 *
 * @author 二哥很猛
 * @since 2019/8/19 15:50
 */
public interface MemberScoreService {

    void signIn(Long memberId);

    SignInVO getSignIn(Long memberId);

    void updateScore(Long memberId, ScoreType scoreType, Integer score, String remark);
}
