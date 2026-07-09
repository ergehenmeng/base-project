package com.eghm.application.member.service.impl;

import com.eghm.domain.member.model.MemberNotice;
import com.eghm.domain.member.model.MemberNoticeLog;
import com.eghm.domain.member.repository.MemberNoticeRepository;
import com.eghm.domain.shared.service.JsonService;
import com.eghm.configuration.authentication.SecurityHolder;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.business.member.SendNotifyRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.SendNotice;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.MessageType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.operate.model.NoticeTemplate;
import com.eghm.application.member.service.MemberNoticeQueryGateway;
import com.eghm.application.member.service.MemberNoticeService;
import com.eghm.application.operate.service.NoticeTemplateService;
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
