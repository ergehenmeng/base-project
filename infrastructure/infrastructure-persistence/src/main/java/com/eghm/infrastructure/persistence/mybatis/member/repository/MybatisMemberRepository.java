package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.eghm.domain.member.repository.MemberRepository;
import com.eghm.domain.member.model.Member;
import com.eghm.domain.shared.enums.MemberState;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberPO;
import com.eghm.utils.DataUtil;
import com.eghm.infrastructure.persistence.mybatis.util.MybatisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * MyBatis adapter for member aggregate persistence.
 */
@Repository
@AllArgsConstructor
public class MybatisMemberRepository implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member findById(Long memberId) {
        return toDomain(memberMapper.selectById(memberId));
    }

    @Override
    public Member findByInviteCode(String inviteCode) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getInviteCode, inviteCode));
    }

    @Override
    public Member findByMpOpenId(String openId) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getMpOpenId, openId));
    }

    @Override
    public Member findByMaOpenId(String openId) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getMaOpenId, openId));
    }

    @Override
    public Member findByEmail(String email) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getEmail, email));
    }

    @Override
    public Member findByMobile(String mobile) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getMobile, mobile));
    }

    @Override
    public Member findByAccount(String account) {
        return toDomain(MybatisUtil.getOne(memberMapper, MemberPO::getAccount, account));
    }

    @Override
    public boolean existsByMobile(String mobile) {
        return findByMobile(mobile) != null;
    }

    @Override
    public boolean existsByAccount(String account) {
        return findByAccount(account) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }

    @Override
    public void save(Member member) {
        memberMapper.insert(toPo(member));
    }

    @Override
    public void update(Member member) {
        memberMapper.updateById(toPo(member));
    }

    @Override
    public void updateState(Long memberId, MemberState state) {
        MemberPO member = new MemberPO();
        member.setId(memberId);
        member.setState(state);
        memberMapper.updateById(member);
    }

    @Override
    public int increaseScore(Long memberId, Integer score) {
        return memberMapper.updateScore(memberId, score);
    }

    private Member toDomain(MemberPO memberPO) {
        return DataUtil.copy(memberPO, Member.class);
    }

    private MemberPO toPo(Member member) {
        return DataUtil.copy(member, MemberPO.class);
    }
}
