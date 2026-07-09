package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.member.MemberScoreQueryDTO;
import com.eghm.application.shared.dto.business.member.MemberScoreQueryRequest;
import com.eghm.application.shared.vo.business.member.MemberScoreVO;

import java.util.List;

/**
 * Query port for member score logs.
 */
public interface MemberScoreLogQueryService {

    Page<MemberScoreVO> listPage(Page<MemberScoreVO> page, MemberScoreQueryRequest request);

    List<MemberScoreVO> listClientPage(MemberScoreQueryDTO request);
}
