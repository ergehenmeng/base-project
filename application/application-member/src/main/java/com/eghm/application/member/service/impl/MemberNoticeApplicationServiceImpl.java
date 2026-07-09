package com.eghm.application.member.service.impl;

import com.eghm.domain.member.model.MemberNotice;
import com.eghm.domain.member.model.MemberNoticeLog;
import com.eghm.domain.member.repository.MemberNoticeRepository;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.configuration.template.TemplateEngine;
import com.eghm.application.shared.dto.business.member.SendNotifyRequest;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.SendNotice;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.MessageType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.application.member.query.MemberNoticeQueryService;
import com.eghm.application.member.service.MemberNoticeApplicationService;
import com.eghm.application.operate.service.NoticeTemplateApplicationService;
import com.eghm.application.shared.vo.business.member.MemberNoticeVO;
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
public class MemberNoticeApplicationServiceImpl implements MemberNoticeApplicationService {

    private final JsonService jsonService;

    private final TemplateEngine templateEngine;

    private final NoticeTemplateApplicationService noticeTemplateService;

    private final MemberNoticeRepository memberNoticeRepository;

    private final MemberNoticeQueryService memberNoticeQueryGateway;

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
        mail.create(memberId, messageType, template.getTitle(), content);
        memberNoticeRepository.saveNotice(mail);
    }

    @Override
    public void sendNoticeBatch(SendNotifyRequest request) {
        MemberNoticeLog noticeLog = new MemberNoticeLog();
        noticeLog.initialize(request.getTitle(), request.getContent(), request.getMessageType(), jsonService.toJson(request.getMemberIds()), SecurityHolder.getUserId());
        memberNoticeRepository.saveNoticeLog(noticeLog);
        request.getMemberIds().forEach(memberId -> {
            MemberNotice notice = new MemberNotice();
            notice.createFromLog(memberId, noticeLog.getId(), MessageType.MARKETING, request.getTitle(), request.getContent());
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
