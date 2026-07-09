package com.eghm.domain.member.repository;

import com.eghm.domain.member.model.MemberScoreLog;

/**
 * Member score log repository.
 */
public interface MemberScoreLogRepository {

    void save(MemberScoreLog scoreLog);
}
