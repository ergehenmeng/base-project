package com.eghm.repository.business;

import com.eghm.business.repository.MemberScoreLogRepository;
import com.eghm.mapper.MemberScoreLogMapper;
import com.eghm.business.model.MemberScoreLog;
import com.eghm.po.MemberScoreLogPO;
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
