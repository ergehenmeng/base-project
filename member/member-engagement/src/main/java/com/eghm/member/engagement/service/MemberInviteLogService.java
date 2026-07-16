package com.eghm.member.engagement.service;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.member.engagement.entity.MemberInviteLog;
import com.eghm.member.engagement.vo.MemberInviteVO;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/14
 */
public interface MemberInviteLogService {

    /**
     * 邀请记录
     *
     * @param query    分页查询
     * @param memberId 会员ID
     * @return 列表
     */
    List<MemberInviteVO> getByPage(PagingQuery query, Long memberId);

    /**
     * 添加邀请记录
     *
     * @param inviteLog log
     */
    void insert(MemberInviteLog inviteLog);
}
