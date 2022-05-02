package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.common.enums.EmailType;
import com.eghm.configuration.task.config.TaskDetail;
import com.eghm.model.dto.email.SendEmail;
import com.eghm.service.common.EmailService;
import com.eghm.service.common.TaskAlarmService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author 殿小二
 * @date 2020/9/18
 */
@Service("taskAlarmService")
@AllArgsConstructor
public class TaskAlarmServiceImpl implements TaskAlarmService {

    private final EmailService emailService;

    @Override
    public void noticeAlarm(TaskDetail detail, String errorMsg) {
        // 发送报警邮件通知
        if (StrUtil.isNotBlank(detail.getAlarmEmail())) {
            SendEmail sendEmail = new SendEmail();
            sendEmail.setType(EmailType.TASK_ALARM);
            sendEmail.setEmail(detail.getAlarmEmail());
            sendEmail.put("errorMsg", errorMsg);
            sendEmail.put("nid", detail.getNid());
            emailService.sendEmail(sendEmail);
        }
    }
}
