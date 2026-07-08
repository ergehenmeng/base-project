package com.eghm.query.business;

import com.eghm.dto.ext.Page;
import com.eghm.query.MybatisPageUtil;
import com.eghm.mapper.MemberInviteLogMapper;
import com.eghm.service.business.MemberInviteLogQueryGateway;
import com.eghm.vo.business.member.MemberInviteVO;
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





