package com.eghm.infrastructure.persistence.mybatis.member.query;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.infrastructure.persistence.mybatis.query.MybatisPageUtil;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberInviteLogMapper;
import com.eghm.application.member.port.out.MemberInviteLogQueryGateway;
import com.eghm.application.shared.vo.business.member.MemberInviteVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MybatisMemberInviteLogQueryGateway implements MemberInviteLogQueryGateway {

    private final MemberInviteLogMapper memberInviteLogMapper;

    @Override
    public List<MemberInviteVO> getByPage(Page<MemberInviteVO> page, Long memberId) {
        return memberInviteLogMapper.getByPage(MybatisPageUtil.toMybatis(page), memberId);
    }
}





