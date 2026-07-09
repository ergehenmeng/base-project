package com.eghm.infrastructure.persistence.mybatis.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.application.shared.dto.business.member.MemberQueryRequest;
import com.eghm.application.shared.dto.business.statistics.DateRequest;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberMapper;
import com.eghm.application.member.port.out.MemberQueryGateway;
import com.eghm.application.shared.vo.business.member.MemberResponse;
import com.eghm.application.shared.vo.business.statistics.MemberRegisterVO;
import com.eghm.application.shared.vo.business.statistics.PieDataVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * MyBatis adapter for member read models.
 */
@Repository
@AllArgsConstructor
public class MybatisMemberQueryGateway implements MemberQueryGateway {

    private final MemberMapper memberMapper;

    @Override
    public Page<MemberResponse> listPage(Page<MemberResponse> page, MemberQueryRequest request) {
        return MybatisPageUtil.fromMybatis(memberMapper.listPage(MybatisPageUtil.toMybatis(page), request));
    }

    @Override
    public List<PieDataVO> channelStatistics(LocalDate startDate, LocalDate endDate) {
        return memberMapper.channelStatistics(startDate, endDate);
    }

    @Override
    public List<PieDataVO> sexStatistics(LocalDate startDate, LocalDate endDate) {
        return memberMapper.sexStatistics(startDate, endDate);
    }

    @Override
    public List<MemberRegisterVO> dayRegister(DateRequest request) {
        return memberMapper.dayRegister(request);
    }

    @Override
    public List<String> listMobile(List<Long> memberIds) {
        return memberMapper.getMobile(memberIds);
    }
}





