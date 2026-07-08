package com.eghm.service.business;

import com.eghm.dto.ext.Page;
import com.eghm.dto.business.member.MemberQueryRequest;
import com.eghm.dto.business.statistics.DateRequest;
import com.eghm.vo.business.member.MemberResponse;
import com.eghm.vo.business.statistics.MemberRegisterVO;
import com.eghm.vo.business.statistics.PieDataVO;

import java.time.LocalDate;
import java.util.List;

/**
 * Query port for member read models and statistics.
 */
public interface MemberQueryGateway {

    Page<MemberResponse> listPage(Page<MemberResponse> page, MemberQueryRequest request);

    List<PieDataVO> channelStatistics(LocalDate startDate, LocalDate endDate);

    List<PieDataVO> sexStatistics(LocalDate startDate, LocalDate endDate);

    List<MemberRegisterVO> dayRegister(DateRequest request);

    List<String> listMobile(List<Long> memberIds);
}
