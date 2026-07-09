package com.eghm.application.member.service;

import com.eghm.dto.ext.Page;
import com.eghm.dto.business.member.MemberScoreQueryDTO;
import com.eghm.dto.business.member.MemberScoreQueryRequest;
import com.eghm.vo.business.member.MemberScoreVO;

import java.util.List;

/**
 * Query port for member score logs.
 */
public interface MemberScoreLogQueryGateway {

    Page<MemberScoreVO> listPage(Page<MemberScoreVO> page, MemberScoreQueryRequest request);

    List<MemberScoreVO> listClientPage(MemberScoreQueryDTO request);
}
