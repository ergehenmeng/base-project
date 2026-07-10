package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.vo.business.member.MemberNoticeVO;

import java.util.List;

/**
 * 会员站内信查询服务
 *
 * @author 殿小二
 * @since 2020/9/11
 */
public interface MemberNoticeQueryService {

    List<MemberNoticeVO> getByPage(Page<MemberNoticeVO> page, Long memberId);

    Long countUnRead(Long memberId);
}
