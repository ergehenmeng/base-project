package com.eghm.member.engagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.foundation.core.service.JsonService;
import com.eghm.integration.messaging.config.template.TemplateEngine;
import com.eghm.member.engagement.dto.SendNotifyRequest;
import com.eghm.foundation.core.dto.ext.PagingQuery;
import com.eghm.foundation.core.configuration.authentication.SecurityHolder;
import com.eghm.integration.messaging.dto.SendNotice;
import com.eghm.foundation.core.enums.ErrorCode;
import com.eghm.foundation.core.enums.MessageType;
import com.eghm.foundation.core.exception.BusinessException;
import com.eghm.member.engagement.mapper.MemberNoticeLogMapper;
import com.eghm.member.engagement.mapper.MemberNoticeMapper;
import com.eghm.member.engagement.entity.MemberNotice;
import com.eghm.member.engagement.entity.MemberNoticeLog;
import com.eghm.business.operation.delivery.entity.NoticeTemplate;
import com.eghm.member.engagement.service.MemberNoticeService;
import com.eghm.business.operation.delivery.service.NoticeTemplateService;
import com.eghm.foundation.web.utility.DataUtil;
import com.eghm.member.engagement.vo.MemberNoticeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 殿小二
 * @since 2020/9/11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberNoticeServiceImpl implements MemberNoticeService {

    private final JsonService jsonService;

    private final TemplateEngine templateEngine;

    private final MemberNoticeMapper memberNoticeMapper;

    private final NoticeTemplateService noticeTemplateService;

    private final MemberNoticeLogMapper memberNoticeLogMapper;

    @Override
    public List<MemberNoticeVO> getByPage(PagingQuery query, Long memberId) {
        LambdaQueryWrapper<MemberNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberNotice::getMemberId, memberId);
        wrapper.last(" order by is_read desc, id desc ");
        Page<MemberNotice> page = memberNoticeMapper.selectPage(query.createPage(false), wrapper);
        return DataUtil.copy(page.getRecords(), MemberNoticeVO.class);
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
        memberNoticeMapper.insert(mail);
    }

    @Override
    public void sendNoticeBatch(SendNotifyRequest request) {
        MemberNoticeLog noticeLog = DataUtil.copy(request, MemberNoticeLog.class);
        noticeLog.setParams(jsonService.toJson(request.getMemberIds()));
        noticeLog.setOperatorId(SecurityHolder.getUserId());
        memberNoticeLogMapper.insert(noticeLog);
        request.getMemberIds().forEach(memberId -> {
            MemberNotice notice = new MemberNotice();
            notice.setMessageType(MessageType.MARKETING);
            notice.setTitle(request.getTitle());
            notice.setContent(request.getContent());
            notice.setMemberId(memberId);
            notice.setNoticeLogId(noticeLog.getId());
            memberNoticeMapper.insert(notice);
        });
    }

    @Override
    public void deleteNotice(Long id, Long memberId) {
        LambdaUpdateWrapper<MemberNotice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberNotice::getId, id);
        wrapper.eq(MemberNotice::getMemberId, memberId);
        memberNoticeMapper.delete(wrapper);
    }

    @Override
    public void setNoticeRead(Long id, Long memberId) {
        LambdaUpdateWrapper<MemberNotice> wrapper = Wrappers.lambdaUpdate();
        wrapper.eq(MemberNotice::getMemberId, memberId);
        wrapper.eq(MemberNotice::getId, id);
        wrapper.set(MemberNotice::getIsRead, true);
        memberNoticeMapper.update(null, wrapper);
    }

    @Override
    public Long countUnRead(Long memberId) {
        LambdaQueryWrapper<MemberNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MemberNotice::getMemberId, memberId);
        wrapper.eq(MemberNotice::getIsRead, false);
        return memberNoticeMapper.selectCount(wrapper);
    }
}
