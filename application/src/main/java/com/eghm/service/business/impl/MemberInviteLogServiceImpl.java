package com.eghm.service.business.impl;

import com.eghm.business.model.MemberInviteLog;
import com.eghm.business.repository.MemberInviteLogRepository;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.service.business.MemberInviteLogQueryGateway;
import com.eghm.service.business.MemberInviteLogService;
import com.eghm.vo.business.member.MemberInviteVO;
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
