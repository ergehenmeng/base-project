package com.eghm.service.business.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.JsonService;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.business.member.SendNotifyRequest;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.SecurityHolder;
import com.eghm.dto.ext.SendNotice;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.MessageType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MemberNoticeLogMapper;
import com.eghm.mapper.MemberNoticeMapper;
import com.eghm.model.MemberNotice;
import com.eghm.model.MemberNoticeLog;
import com.eghm.model.NoticeTemplate;
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
