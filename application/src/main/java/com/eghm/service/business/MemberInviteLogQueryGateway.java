package com.eghm.service.business;

import com.eghm.dto.ext.Page;
import com.eghm.vo.business.member.MemberInviteVO;

import java.util.List;

/**
 * 会员邀请记录查询端口
 *
 * @author 殿小二
 * @since 2020/9/14
 */
public interface MemberInviteLogQueryGateway {

    List<MemberInviteVO> getByPage(Page<MemberInviteVO> page, Long memberId);
}
