package com.eghm.business.repository;

import com.eghm.business.model.MemberInviteLog;

/**
 * 会员邀请记录仓储接口
 *
 * @author 殿小二
 * @since 2020/9/14
 */
public interface MemberInviteLogRepository {

    /**
     * 添加邀请记录
     *
     * @param inviteLog log
     */
    void save(MemberInviteLog inviteLog);
}
