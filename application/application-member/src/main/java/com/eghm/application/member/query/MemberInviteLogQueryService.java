package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.vo.business.member.MemberInviteVO;

import java.util.List;

/**
 * 会员邀请记录查询端口
 *
 * @author 殿小二
 * @since 2020/9/14
 */
public interface MemberInviteLogQueryService {

    List<MemberInviteVO> getByPage(Page<MemberInviteVO> page, Long memberId);
}
