package com.eghm.application.member.service.impl;

import com.eghm.domain.member.repository.MemberScoreLogRepository;
import com.eghm.application.shared.common.SysConfigService;
import com.eghm.constants.ConfigConstant;
import com.eghm.domain.member.model.MemberScoreLog;
import com.eghm.application.member.service.MemberScoreLogApplicationService;
import com.eghm.application.shared.utils.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@AllArgsConstructor
@Service("memberScoreLogService")
public class MemberScoreLogApplicationServiceImpl implements MemberScoreLogApplicationService {

    private final SysConfigService sysConfigService;

    private final MemberScoreLogRepository memberScoreLogRepository;

    @Override
    public void insert(MemberScoreLog scoreLog) {
        memberScoreLogRepository.save(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigService.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

}
