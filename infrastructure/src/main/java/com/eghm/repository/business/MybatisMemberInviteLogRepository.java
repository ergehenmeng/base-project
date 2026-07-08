package com.eghm.repository.business;

import com.eghm.business.model.MemberInviteLog;
import com.eghm.business.repository.MemberInviteLogRepository;
import com.eghm.mapper.MemberInviteLogMapper;
import com.eghm.po.MemberInviteLogPO;
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
