package com.eghm.service.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.eghm.common.enums.ErrorCode;
import com.eghm.common.enums.InMailType;
import com.eghm.common.exception.BusinessException;
import com.eghm.configuration.template.TemplateEngine;
import com.eghm.dao.mapper.UserInMailMapper;
import com.eghm.dao.model.InMailTemplate;
import com.eghm.dao.model.User;
import com.eghm.dao.model.UserInMail;
import com.eghm.model.ext.PushNotice;
import com.eghm.model.ext.SendInMail;
import com.eghm.service.common.InMailTemplateService;
import com.eghm.service.common.PushService;
import com.eghm.service.user.UserInMailService;
import com.eghm.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author 殿小二
 * @date 2020/9/11
 */
@Service("userInMailService")
@Slf4j
public class UserInMailServiceImpl implements UserInMailService {

    private InMailTemplateService inMailTemplateService;

    private UserInMailMapper userInMailMapper;

    private TemplateEngine templateEngine;

    private UserService userService;

    private PushService pushService;

    @Autowired
    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setInMailTemplateService(InMailTemplateService inMailTemplateService) {
        this.inMailTemplateService = inMailTemplateService;
    }

    @Autowired
    public void setUserInMailMapper(UserInMailMapper userInMailMapper) {
        this.userInMailMapper = userInMailMapper;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendInMail(Integer userId, SendInMail inMail) {
        InMailType mailType = inMail.getInMailType();
        InMailTemplate template = inMailTemplateService.getTemplate(mailType.getValue());
        if (template == null) {
            log.warn("站内性模板未配置 [{}]", mailType.getValue());
            throw new BusinessException(ErrorCode.IN_MAIL_NULL);
        }
        String content = templateEngine.render(template.getContent(), inMail.getParams());
        UserInMail mail = new UserInMail();
        mail.setClassify(mailType.getValue());
        mail.setTitle(template.getTitle());
        mail.setContent(content);
        mail.setUserId(userId);
        userInMailMapper.insertSelective(mail);
        // 发送推送消息
        if (mailType.isPushNotice()) {
            this.doSendNotice(mail, mailType, inMail.getExtras());
        }
    }

    /**
     * 拼接通知消息信息并调用极光发送推送
     * @param userInMail 通知信息
     * @param mailType 消息类型
     * @param extras 消息发送时附加的参数
     */
    private void doSendNotice(UserInMail userInMail, InMailType mailType, Map<String, String> extras) {
        User user = userService.getById(userInMail.getUserId());
        PushNotice notice = PushNotice.builder()
                .alias(user.getMobile())
                .content(userInMail.getContent())
                .title(userInMail.getTitle())
                .viewTag(mailType.getViewTag()).build();
        if (CollUtil.isNotEmpty(extras)) {
            notice.getExtras().putAll(extras);
        }
        pushService.pushNotification(notice);
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void sendInMail(List<Integer> userIdList, SendInMail inMail) {
        userIdList.forEach(userId -> this.sendInMail(userId, inMail));
    }
}
