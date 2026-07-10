package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.member.MemberQueryRequest;
import com.eghm.application.shared.dto.business.statistics.DateRequest;
import com.eghm.application.shared.vo.business.member.MemberResponse;
import com.eghm.application.shared.vo.business.statistics.MemberRegisterVO;
import com.eghm.application.shared.vo.business.statistics.PieDataVO;

import java.time.LocalDate;
import java.util.List;

/**
 * Member read model and statistics query service.
 */
public interface MemberQueryService {

    Page<MemberResponse> listPage(Page<MemberResponse> page, MemberQueryRequest request);

    List<PieDataVO> channelStatistics(LocalDate startDate, LocalDate endDate);

    List<PieDataVO> sexStatistics(LocalDate startDate, LocalDate endDate);

    List<MemberRegisterVO> dayRegister(DateRequest request);

    List<String> listMobile(List<Long> memberIds);
}
