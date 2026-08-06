package com.eghm.member.engagement.service.impl;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.member.engagement.mapper.MemberInviteLogMapper;
import com.eghm.member.engagement.entity.MemberInviteLog;
import com.eghm.member.engagement.service.MemberInviteLogService;
import com.eghm.member.engagement.vo.MemberInviteVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
@Service
@AllArgsConstructor
public class MemberInviteLogServiceImpl implements MemberInviteLogService {

    private final MemberInviteLogMapper memberInviteLogMapper;

    @Override
    public List<MemberInviteVO> getByPage(PagingQuery query, Long memberId) {
        return memberInviteLogMapper.getByPage(query.createPage(false), memberId);
    }

    @Override
    public void insert(MemberInviteLog inviteLog) {
        memberInviteLogMapper.insert(inviteLog);
    }
}
