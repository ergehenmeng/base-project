package com.eghm.infrastructure.persistence.mybatis.member.repository;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.domain.member.model.MemberNotice;
import com.eghm.domain.member.model.MemberNoticeLog;
import com.eghm.domain.member.repository.MemberNoticeRepository;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberNoticeLogMapper;
import com.eghm.infrastructure.persistence.mybatis.mapper.MemberNoticeMapper;
import com.eghm.infrastructure.persistence.mybatis.po.MemberNoticeLogPO;
import com.eghm.infrastructure.persistence.mybatis.po.MemberNoticePO;
import com.eghm.application.shared.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisMemberNoticeRepository implements MemberNoticeRepository {

    private final MemberNoticeMapper memberNoticeMapper;

    private final MemberNoticeLogMapper memberNoticeLogMapper;

    @Override
    public void saveNotice(MemberNotice notice) {
        memberNoticeMapper.insert(DataUtil.copy(notice, MemberNoticePO.class));
    }

    @Override
    public void saveNoticeLog(MemberNoticeLog noticeLog) {
        MemberNoticeLogPO noticeLogPO = DataUtil.copy(noticeLog, MemberNoticeLogPO.class);
        memberNoticeLogMapper.insert(noticeLogPO);
        noticeLog.setId(noticeLogPO.getId());
    }

    @Override
    public void deleteNotice(Long id, Long memberId) {
        LambdaUpdateWrapper<MemberNoticePO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberNoticePO::getId, id);
        wrapper.eq(MemberNoticePO::getMemberId, memberId);
        memberNoticeMapper.delete(wrapper);
    }

    @Override
    public void markRead(Long id, Long memberId) {
        LambdaUpdateWrapper<MemberNoticePO> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberNoticePO::getMemberId, memberId);
        wrapper.eq(MemberNoticePO::getId, id);
        wrapper.set(MemberNoticePO::getIsRead, true);
        memberNoticeMapper.update(null, wrapper);
    }
}
