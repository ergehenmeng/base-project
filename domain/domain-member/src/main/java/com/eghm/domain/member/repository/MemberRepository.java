package com.eghm.domain.member.repository;

import com.eghm.domain.shared.enums.MemberState;
import com.eghm.domain.member.model.Member;

/**
 * Member aggregate repository.
 */
public interface MemberRepository {

    Member findById(Long memberId);

    Member findByInviteCode(String inviteCode);

    Member findByMpOpenId(String openId);

    Member findByMaOpenId(String openId);

    Member findByEmail(String email);

    Member findByMobile(String mobile);

    Member findByAccount(String account);

    boolean existsByMobile(String mobile);

    boolean existsByAccount(String account);

    boolean existsByEmail(String email);

    void save(Member member);

    void update(Member member);

    void updateState(Long memberId, MemberState state);

    int increaseScore(Long memberId, Integer score);
}
