package com.eghm.application.member.service.impl;

import com.eghm.dto.ext.Page;
import com.eghm.domain.member.repository.MemberScoreLogRepository;
import com.eghm.common.impl.SysConfigApi;
import com.eghm.constants.ConfigConstant;
import com.eghm.dto.business.member.MemberScoreQueryDTO;
import com.eghm.dto.business.member.MemberScoreQueryRequest;
import com.eghm.domain.member.model.MemberScoreLog;
import com.eghm.application.member.service.MemberScoreLogQueryGateway;
import com.eghm.application.member.service.MemberScoreLogService;
import com.eghm.utils.StringUtil;
import com.eghm.vo.business.member.MemberScoreVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/5
 */
@AllArgsConstructor
@Service("memberScoreLogService")
public class MemberScoreLogServiceImpl implements MemberScoreLogService {

    private final SysConfigApi sysConfigApi;

    private final MemberScoreLogRepository memberScoreLogRepository;

    private final MemberScoreLogQueryGateway memberScoreLogQueryGateway;

    @Override
    public Page<MemberScoreVO> getByPage(MemberScoreQueryRequest request) {
        return memberScoreLogQueryGateway.listPage(request.createPage(), request);
    }

    @Override
    public List<MemberScoreVO> clientByPage(MemberScoreQueryDTO request) {
        return memberScoreLogQueryGateway.listClientPage(request);
    }

    @Override
    public void insert(MemberScoreLog scoreLog) {
        memberScoreLogRepository.save(scoreLog);
    }

    @Override
    public int getSignInScore() {
        return StringUtil.random(1, sysConfigApi.getInt(ConfigConstant.SIGN_IN_SCORE));
    }

}
