package com.eghm.service.business.impl;

import com.eghm.business.model.MemberNotice;
import com.eghm.business.model.MemberNoticeLog;
import com.eghm.business.repository.MemberNoticeRepository;
import com.eghm.common.JsonService;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.business.member.SendNotifyRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.SendNotice;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.MessageType;
import com.eghm.exception.BusinessException;
import com.eghm.operate.model.NoticeTemplate;
import com.eghm.service.business.MemberNoticeQueryGateway;
import com.eghm.service.business.MemberNoticeService;
import com.eghm.service.operate.NoticeTemplateService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.business.member.MemberNoticeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/11
 */
@Slf4j
@RequiredArgsConstructor
@Service("memberNoticeService")
public class MemberNoticeServiceImpl implements MemberNoticeService {

    private final JsonService jsonService;

    private final TemplateEngine templateEngine;

    private final NoticeTemplateService noticeTemplateService;

    private final MemberNoticeRepository memberNoticeRepository;

    private final MemberNoticeQueryGateway memberNoticeQueryGateway;

    @Override
    public List<MemberNoticeVO> getByPage(PagingQuery query, Long memberId) {
        return memberNoticeQueryGateway.getByPage(query.createPage(false), memberId);
    }

    @Override
    public void sendNotice(Long memberId, SendNotice sendNotice) {
        MessageType messageType = sendNotice.getMessageType();
        NoticeTemplate template = noticeTemplateService.getTemplate(messageType.getValue());
        if (template == null) {
            log.warn("站内性模板未配置 [{}]", messageType.getValue());
            throw new BusinessException(ErrorCode.IN_MAIL_NULL);
        }
        String content = templateEngine.render(template.getContent(), sendNotice.getParams());
        MemberNotice mail = new MemberNotice();
        mail.setMessageType(messageType);
        mail.setTitle(template.getTitle());
        mail.setContent(content);
        mail.setMemberId(memberId);
        memberNoticeRepository.saveNotice(mail);
    }

    @Override
    public void sendNoticeBatch(SendNotifyRequest request) {
        MemberNoticeLog noticeLog = DataUtil.copy(request, MemberNoticeLog.class);
        noticeLog.setParams(jsonService.toJson(request.getMemberIds()));
        noticeLog.setOperatorId(SecurityHolder.getUserId());
        memberNoticeRepository.saveNoticeLog(noticeLog);
        request.getMemberIds().forEach(memberId -> {
            MemberNotice notice = new MemberNotice();
            notice.setMessageType(MessageType.MARKETING);
            notice.setTitle(request.getTitle());
            notice.setContent(request.getContent());
            notice.setMemberId(memberId);
            notice.setNoticeLogId(noticeLog.getId());
            memberNoticeRepository.saveNotice(notice);
        });
    }

    @Override
    public void deleteNotice(Long id, Long memberId) {
        memberNoticeRepository.deleteNotice(id, memberId);
    }

    @Override
    public void setNoticeRead(Long id, Long memberId) {
        memberNoticeRepository.markRead(id, memberId);
    }

    @Override
    public Long countUnRead(Long memberId) {
        return memberNoticeQueryGateway.countUnRead(memberId);
    }
}
