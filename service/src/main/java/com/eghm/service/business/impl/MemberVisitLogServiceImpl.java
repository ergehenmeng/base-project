package com.eghm.service.business.impl;

import com.eghm.dto.DateRequest;
import com.eghm.mapper.MemberVisitLogMapper;
import com.eghm.model.MemberVisitLog;
import com.eghm.service.business.MemberVisitLogService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.statistics.MemberVisitVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户浏览记录表 服务实现类
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-23
 */
@Slf4j
@AllArgsConstructor
@Service("memberVisitLogService")
public class MemberVisitLogServiceImpl implements MemberVisitLogService {

    private final MemberVisitLogMapper memberVisitLogMapper;

    @Override
    public void insert(MemberVisitLog memberVisitLog) {
        memberVisitLogMapper.insert(memberVisitLog);
    }

    @Override
    public List<MemberVisitVO> dayVisit(DateRequest request) {
        List<MemberVisitVO> voList = memberVisitLogMapper.dayVisit(request);
        Map<LocalDate, MemberVisitVO> voMap = voList.stream().collect(Collectors.toMap(MemberVisitVO::getCreateDate, Function.identity()));
        return DataUtil.paddingDay(voMap, request.getStartDate(), request.getEndDate(), MemberVisitVO::new);
    }
}
