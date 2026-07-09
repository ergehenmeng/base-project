package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.eghm.domain.member.repository.MemberScoreLogRepository;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberScoreLogMapper;
import com.eghm.domain.member.model.MemberScoreLog;
import com.eghm.infrastructure.persistence.mybatis.po.MemberScoreLogPO;
import com.eghm.utils.DataUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis adapter for member score log persistence.
 */
@Repository
@AllArgsConstructor
public class MybatisMemberScoreLogRepository implements MemberScoreLogRepository {

    private final MemberScoreLogMapper memberScoreLogMapper;

    @Override
    public void save(MemberScoreLog scoreLog) {
        memberScoreLogMapper.insert(DataUtil.copy(scoreLog, MemberScoreLogPO.class));
    }
}
