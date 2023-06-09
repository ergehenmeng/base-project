package com.eghm.service.member;

import com.eghm.model.MemberInviteLog;

/**
 * @author 殿小二
 * @date 2020/9/14
 */
public interface MemberInviteLogService {

    /**
     * 添加邀请记录
     * @param inviteLog log
     */
    void insert(MemberInviteLog inviteLog);
}
