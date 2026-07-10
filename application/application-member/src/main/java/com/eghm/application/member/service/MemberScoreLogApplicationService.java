package com.eghm.application.member.service;

import com.eghm.application.shared.common.SysConfigService;
import com.eghm.application.shared.utils.StringUtil;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.member.model.MemberScoreLog;
import com.eghm.domain.member.repository.MemberScoreLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@Service
@AllArgsConstructor
public class MemberScoreLogApplicationService {
    
    private final SysConfigService sysConfigService;
    
    private final MemberScoreLogRepository memberScoreLogRepository;
    
    /**
     * 添加积分信息
     *
     * @param scoreLog 积分
     */
    public void insert(MemberScoreLog scoreLog) {
        memberScoreLogRepository.save(scoreLog);
    }
    /**
     * 获取每日签到积分数 (随机,且由系统参数影响)
     *
     * @return 积分数
     */
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigService.getInt(ConfigConstant.SIGN_IN_SCORE));
    }
}
