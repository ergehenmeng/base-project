package com.eghm.service.business;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.model.MemberInviteLog;
import com.eghm.vo.business.member.MemberInviteVO;

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
