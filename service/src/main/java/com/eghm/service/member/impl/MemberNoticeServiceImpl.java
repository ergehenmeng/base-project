package com.eghm.service.member.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.PushNotice;
import com.eghm.dto.ext.SendNotice;
import com.eghm.dto.member.tag.SendNotifyRequest;
import com.eghm.enums.ErrorCode;
import com.eghm.enums.NoticeType;
import com.eghm.exception.BusinessException;
import com.eghm.mapper.MemberNoticeLogMapper;
import com.eghm.mapper.MemberNoticeMapper;
import com.eghm.model.Member;
import com.eghm.model.MemberNotice;
import com.eghm.model.MemberNoticeLog;
import com.eghm.model.NoticeTemplate;
import com.eghm.service.common.NoticeTemplateService;
import com.eghm.common.PushService;
import com.eghm.service.member.MemberNoticeService;
import com.eghm.service.member.MemberService;
import com.eghm.utils.DataUtil;
import com.eghm.vo.member.MemberNoticeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 殿小二
 * @since 2020/9/11
 */
@Slf4j
@RequiredArgsConstructor
@Service("memberNoticeService")
public class MemberNoticeServiceImpl implements MemberNoticeService {

    private final NoticeTemplateService noticeTemplateService;

    private final MemberNoticeMapper memberNoticeMapper;

    private final MemberNoticeLogMapper memberNoticeLogMapper;

    private final TemplateEngine templateEngine;

    private final MemberService memberService;

    private PushService pushService;

    @Autowired(required = false)
    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

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
        NoticeType mailType = sendNotice.getNoticeType();
        NoticeTemplate template = noticeTemplateService.getTemplate(mailType.getValue());
        if (template == null) {
            log.warn("站内性模板未配置 [{}]", mailType.getValue());
            throw new BusinessException(ErrorCode.IN_MAIL_NULL);
        }
        String content = templateEngine.render(template.getContent(), sendNotice.getParams());
        MemberNotice mail = new MemberNotice();
        mail.setNoticeType(mailType);
        mail.setTitle(template.getTitle());
        mail.setContent(content);
        mail.setMemberId(memberId);
        memberNoticeMapper.insert(mail);
        // 发送推送消息
        if (mailType.isPushNotice()) {
            this.doSendNotice(mail, mailType, sendNotice.getExtras());
        }
    }

    /**
     * 拼接通知消息信息并调用极光发送推送
     *
     * @param memberNotice 通知信息
     * @param mailType     消息类型
     * @param extras       消息发送时附加的参数
     */
    private void doSendNotice(MemberNotice memberNotice, NoticeType mailType, Map<String, String> extras) {
        if (pushService == null) {
            log.warn("推送服务未配置 [{}] [{}] [{}]", memberNotice.getMemberId(), memberNotice.getContent(), mailType);
            return;
        }
        Member member = memberService.getById(memberNotice.getMemberId());
        PushNotice notice = PushNotice.builder()
                .alias(member.getMobile())
                .content(memberNotice.getContent())
                .title(memberNotice.getTitle())
                .viewTag(mailType.getViewTag()).build();
        if (CollUtil.isNotEmpty(extras)) {
            notice.getExtras().putAll(extras);
        }
        pushService.pushNotification(notice);
    }

    @Override
    public void sendNoticeBatch(SendNotifyRequest request) {
        MemberNoticeLog noticeLog = DataUtil.copy(request, MemberNoticeLog.class);
        memberNoticeLogMapper.insert(noticeLog);
        request.getMemberIds().forEach(memberId -> {
            MemberNotice notice = new MemberNotice();
            notice.setNoticeType(NoticeType.MARKETING);
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
