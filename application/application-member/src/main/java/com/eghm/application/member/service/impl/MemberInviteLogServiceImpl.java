package com.eghm.application.member.service.impl;

import com.eghm.domain.member.model.MemberInviteLog;
import com.eghm.domain.member.repository.MemberInviteLogRepository;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.member.port.out.MemberInviteLogQueryGateway;
import com.eghm.application.member.port.in.MemberInviteLogService;
import com.eghm.application.shared.vo.business.member.MemberInviteVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
@AllArgsConstructor
@Service("memberInviteLogService")
public class MemberInviteLogServiceImpl implements MemberInviteLogService {

    private final MemberInviteLogRepository memberInviteLogRepository;

    private final MemberInviteLogQueryGateway memberInviteLogQueryGateway;

    @Override
    public List<MemberInviteVO> getByPage(PagingQuery query, Long memberId) {
        return memberInviteLogQueryGateway.getByPage(query.createPage(false), memberId);
    }

    @Override
    public void insert(MemberInviteLog inviteLog) {
        memberInviteLogRepository.save(inviteLog);
    }
}
