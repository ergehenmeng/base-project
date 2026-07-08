package com.eghm.business.repository;

import com.eghm.business.model.MemberNotice;
import com.eghm.business.model.MemberNoticeLog;

/**
 * 会员站内信仓储接口
 *
 * @author 殿小二
 * @since 2020/9/11
 */
public interface MemberNoticeRepository {

    void saveNotice(MemberNotice notice);

    void saveNoticeLog(MemberNoticeLog noticeLog);

    void deleteNotice(Long id, Long memberId);

    void markRead(Long id, Long memberId);
}
