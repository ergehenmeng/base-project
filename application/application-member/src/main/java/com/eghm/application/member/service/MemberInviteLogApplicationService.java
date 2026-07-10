package com.eghm.application.member.service;

import com.eghm.domain.member.model.MemberInviteLog;
import com.eghm.domain.member.repository.MemberInviteLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
@Service
@AllArgsConstructor
public class MemberInviteLogApplicationService {
    
    private final MemberInviteLogRepository memberInviteLogRepository;
    
    /**
     * 添加邀请记录
     *
     * @param inviteLog log
     */
    public void insert(MemberInviteLog inviteLog) {
        memberInviteLogRepository.save(inviteLog);
    }
}
