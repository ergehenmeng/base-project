package com.eghm.application.member.service;

import com.eghm.application.shared.configuration.authentication.SecurityHolder;
import com.eghm.application.shared.configuration.template.TemplateEngine;
import com.eghm.application.shared.common.NoticeTemplateProvider;
import com.eghm.application.shared.dto.business.member.SendNotifyRequest;
import com.eghm.application.shared.dto.ext.SendNotice;
import com.eghm.application.shared.vo.operate.template.NoticeTemplateResponse;
import com.eghm.domain.member.model.MemberNotice;
import com.eghm.domain.member.model.MemberNoticeLog;
import com.eghm.domain.member.repository.MemberNoticeRepository;
import com.eghm.domain.shared.enums.ErrorCode;
import com.eghm.domain.shared.enums.MessageType;
import com.eghm.domain.shared.exception.BusinessException;
import com.eghm.domain.shared.service.JsonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @since 2020/9/11
 */
@Slf4j
@Service
@AllArgsConstructor
public class MemberNoticeApplicationService {
    
    private final JsonService jsonService;
    
    private final TemplateEngine templateEngine;
    
    private final NoticeTemplateProvider noticeTemplateProvider;
    
    private final MemberNoticeRepository memberNoticeRepository;
    
    /**
     * 发送站内信
     *
     * @param memberId   接收消息的用户
     * @param sendNotice 消息内容
     */
    public void sendNotice(Long memberId, SendNotice sendNotice) {
        MessageType messageType = sendNotice.getMessageType();
        NoticeTemplateResponse template = noticeTemplateProvider.getNoticeTemplate(messageType.getValue());
        if (template == null) {
            log.warn("站内性模板未配置 [{}]", messageType.getValue());
            throw new BusinessException(ErrorCode.IN_MAIL_NULL);
        }
        String content = templateEngine.render(template.getContent(), sendNotice.getParams());
        MemberNotice mail = new MemberNotice();
        mail.create(memberId, messageType, template.getTitle(), content);
        memberNoticeRepository.saveNotice(mail);
    }

    /**
     * 批量发送站内信
     *
     * @param request 发送消息
     */
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

    /**
     * 删除消息通知
     *
     * @param id       主键id
     * @param memberId memberId
     */
    public void deleteNotice(Long id, Long memberId) {
        memberNoticeRepository.deleteNotice(id, memberId);
    }

    /**
     * 设置消息已读
     *
     * @param id       id
     * @param memberId memberId
     */
    public void setNoticeRead(Long id, Long memberId) {
        memberNoticeRepository.markRead(id, memberId);
    }

}
