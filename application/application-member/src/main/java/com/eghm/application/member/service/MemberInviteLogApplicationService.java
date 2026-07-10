package com.eghm.application.member.service;

import com.eghm.domain.member.model.MemberInviteLog;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
public interface MemberInviteLogApplicationService {

    /**
     * 添加邀请记录
     *
     * @param inviteLog log
     */
    void insert(MemberInviteLog inviteLog);
}
