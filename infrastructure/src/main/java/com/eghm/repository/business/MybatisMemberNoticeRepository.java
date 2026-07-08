package com.eghm.repository.business;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.eghm.business.model.MemberNotice;
import com.eghm.business.model.MemberNoticeLog;
import com.eghm.business.repository.MemberNoticeRepository;
import com.eghm.mapper.MemberNoticeLogMapper;
import com.eghm.mapper.MemberNoticeMapper;
import com.eghm.po.MemberNoticeLogPO;
import com.eghm.po.MemberNoticePO;
import com.eghm.utils.DataUtil;
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
