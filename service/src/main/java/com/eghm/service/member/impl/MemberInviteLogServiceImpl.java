package com.eghm.service.member.impl;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.mapper.MemberInviteLogMapper;
import com.eghm.model.MemberInviteLog;
import com.eghm.service.member.MemberInviteLogService;
import com.eghm.vo.member.MemberInviteVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
@Service("memberInviteLogService")
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
