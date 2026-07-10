package com.eghm.application.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.business.member.MemberQueryRequest;
import com.eghm.application.shared.dto.business.statistics.DateRequest;
import com.eghm.application.shared.utils.DataUtil;
import com.eghm.application.shared.vo.business.member.MemberResponse;
import com.eghm.application.shared.vo.business.statistics.MemberRegisterVO;
import com.eghm.application.shared.vo.business.statistics.MemberStatisticsVO;
import com.eghm.application.shared.vo.business.statistics.PieDataVO;
import com.eghm.domain.shared.enums.Channel;
import com.eghm.domain.shared.enums.Gender;
import com.eghm.domain.shared.enums.SelectType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Member read model and statistics query service.
 */
public interface MemberQueryService {

    Page<MemberResponse> listPage(Page<MemberResponse> page, MemberQueryRequest request);

    List<PieDataVO> channelStatistics(LocalDate startDate, LocalDate endDate);

    List<PieDataVO> sexStatistics(LocalDate startDate, LocalDate endDate);

    List<MemberRegisterVO> dayRegister(DateRequest request);

    List<String> listMobile(List<Long> memberIds);

    default MemberStatisticsVO sexChannelStatistics(DateRequest request) {
        List<PieDataVO> statistics = this.channelStatistics(request.getStartDate(), request.getEndDate());
        Map<String, PieDataVO> channelMap = statistics.stream().collect(Collectors.toMap(PieDataVO::getName, Function.identity()));
        List<PieDataVO> channelList = new ArrayList<>(Channel.values().length);
        for (Channel value : Channel.values()) {
            channelList.add(channelMap.getOrDefault(value.name(), new PieDataVO(value.name())));
        }

        List<PieDataVO> sexStatistics = this.sexStatistics(request.getStartDate(), request.getEndDate());
        Map<String, PieDataVO> sexMap = sexStatistics.stream().collect(Collectors.toMap(PieDataVO::getName, Function.identity()));
        List<PieDataVO> sexList = new ArrayList<>(Gender.values().length);
        for (Gender value : Gender.values()) {
            sexList.add(sexMap.getOrDefault(value.getName(), new PieDataVO(value.getName())));
        }

        MemberStatisticsVO vo = new MemberStatisticsVO();
        vo.setChannelList(channelList);
        vo.setSexList(sexList);
        return vo;
    }

    default List<MemberRegisterVO> dayRegisterStatistics(DateRequest request) {
        List<MemberRegisterVO> voList = this.dayRegister(request);
        if (request.getSelectType() == SelectType.YEAR) {
            Map<String, MemberRegisterVO> voMap = voList.stream().collect(Collectors.toMap(MemberRegisterVO::getCreateMonth, Function.identity()));
            return DataUtil.paddingMonth(voMap, request.getStartDate(), request.getEndDate(), MemberRegisterVO::new);
        }
        Map<LocalDate, MemberRegisterVO> voMap = voList.stream().collect(Collectors.toMap(MemberRegisterVO::getCreateDate, Function.identity()));
        return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), MemberRegisterVO::new);
    }
}
