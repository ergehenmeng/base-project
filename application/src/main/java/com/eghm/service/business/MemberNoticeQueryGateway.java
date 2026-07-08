package com.eghm.service.business;

import com.eghm.dto.ext.Page;
import com.eghm.vo.business.member.MemberNoticeVO;

import java.util.List;

/**
 * 会员站内信查询端口
 *
 * @author 殿小二
 * @since 2020/9/11
 */
public interface MemberNoticeQueryGateway {

    List<MemberNoticeVO> getByPage(Page<MemberNoticeVO> page, Long memberId);

    Long countUnRead(Long memberId);
}
