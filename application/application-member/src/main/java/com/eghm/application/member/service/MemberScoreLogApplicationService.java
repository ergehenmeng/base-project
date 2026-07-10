package com.eghm.application.member.service;

import com.eghm.domain.member.model.MemberScoreLog;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
public interface MemberScoreLogApplicationService {

    /**
     * 添加积分信息
     *
     * @param scoreLog 积分
     */
    void insert(MemberScoreLog scoreLog);

    /**
     * 获取每日签到积分数 (随机,且由系统参数影响)
     *
     * @return 积分数
     */
    int getSignInScore();
}
