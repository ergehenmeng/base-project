package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.eghm.domain.member.model.MemberInviteLog;
import com.eghm.domain.member.repository.MemberInviteLogRepository;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberInviteLogMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberInviteLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MybatisMemberInviteLogRepository implements MemberInviteLogRepository {

    private final MemberInviteLogMapper memberInviteLogMapper;

    @Override
    public void save(MemberInviteLog inviteLog) {
        memberInviteLogMapper.insert(DataUtil.copy(inviteLog, MemberInviteLogPO.class));
    }
}
