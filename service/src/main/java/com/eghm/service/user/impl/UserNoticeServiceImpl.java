package com.eghm.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.NoticeType;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.mapper.UserNoticeMapper;
import com.eghm.model.NoticeTemplate;
import com.eghm.model.User;
import com.eghm.model.UserNotice;
import com.eghm.model.dto.ext.PageData;
import com.eghm.model.dto.ext.PagingQuery;
import com.eghm.model.dto.ext.PushNotice;
import com.eghm.model.dto.ext.SendNotice;
import com.eghm.model.vo.user.UserNoticeVO;
import com.eghm.service.common.NoticeTemplateService;
import com.eghm.service.common.PushService;
import com.eghm.service.user.UserNoticeService;
import com.eghm.service.user.UserService;
import com.eghm.utils.DataUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/9/11
 */
@Service("userNoticeService")
@Slf4j
public class UserNoticeServiceImpl implements UserNoticeService {

    private NoticeTemplateService noticeTemplateService;

    private UserNoticeMapper userNoticeMapper;

    private TemplateEngine templateEngine;

    private UserService userService;

    private PushService pushService;

    @Autowired(required = false)
    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setNoticeTemplateService(NoticeTemplateService noticeTemplateService) {
        this.noticeTemplateService = noticeTemplateService;
    }

    @Autowired
    public void setUserNoticeMapper(UserNoticeMapper userNoticeMapper) {
        this.userNoticeMapper = userNoticeMapper;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendNotice(Long userId, SendNotice sendNotice) {
        NoticeType mailType = sendNotice.getNoticeType();
        NoticeTemplate template = noticeTemplateService.getTemplate(mailType.getValue());
        if (template == null) {
            log.warn("站内性模板未配置 [{}]", mailType.getValue());
            throw new BusinessException(ErrorCode.IN_MAIL_NULL);
        }
        String content = templateEngine.render(template.getContent(), sendNotice.getParams());
        UserNotice mail = new UserNotice();
        mail.setClassify(mailType.getValue());
        mail.setTitle(template.getTitle());
        mail.setContent(content);
        mail.setUserId(userId);
        userNoticeMapper.insert(mail);
        // 发送推送消息
        if (mailType.isPushNotice()) {
            this.doSendNotice(mail, mailType, sendNotice.getExtras());
        }
    }

    /**
     * 拼接通知消息信息并调用极光发送推送
     * @param userNotice 通知信息
     * @param mailType 消息类型
     * @param extras 消息发送时附加的参数
     */
    private void doSendNotice(UserNotice userNotice, NoticeType mailType, Map<String, String> extras) {
        User user = userService.getById(userNotice.getUserId());
        PushNotice notice = PushNotice.builder()
                .alias(user.getMobile())
                .content(userNotice.getContent())
                .title(userNotice.getTitle())
                .viewTag(mailType.getViewTag()).build();
        if (CollUtil.isNotEmpty(extras)) {
            notice.getExtras().putAll(extras);
        }
        pushService.pushNotification(notice);
    }


    @Override
    public void sendNotice(List<Long> userIdList, SendNotice sendNotice) {
        userIdList.forEach(userId -> this.sendNotice(userId, sendNotice));
    }

    @Override
    public PageData<UserNoticeVO> getByPage(PagingQuery query, Long userId) {
        LambdaQueryWrapper<UserNotice> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserNotice::getUserId, userId);
        wrapper.last(" order by read desc, id desc ");
        Page<UserNotice> page = userNoticeMapper.selectPage(query.createPage(), wrapper);
        return DataUtil.convert(page, userNotice -> DataUtil.copy(userNotice, UserNoticeVO.class));
    }

    @Override
    public void deleteNotice(Long id, Long userId) {
        LambdaUpdateWrapper<UserNotice> wrapper = Wrappers.lambdaUpdate();
        wrapper.set(UserNotice::getId, id);
        wrapper.set(UserNotice::getUserId, userId);
        userNoticeMapper.delete(wrapper);
    }

    @Override
    public void setNoticeRead(Long id, Long userId) {
        UserNotice notice = new UserNotice();
        notice.setId(id);
        notice.setUserId(userId);
        notice.setRead(true);
        userNoticeMapper.updateById(notice);
    }
}
