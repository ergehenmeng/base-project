package com.eghm.service.member.impl;

import com.eghm.mapper.MemberInviteLogMapper;
import com.eghm.model.MemberInviteLog;
import com.eghm.service.member.MemberInviteLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
@Service("memberInviteLogService")
@AllArgsConstructor
public class MemberInviteLogServiceImpl implements MemberInviteLogService {

    private final MemberInviteLogMapper memberInviteLogMapper;

    @Override
    public void insert(MemberInviteLog inviteLog) {
        memberInviteLogMapper.insert(inviteLog);
    }
}
