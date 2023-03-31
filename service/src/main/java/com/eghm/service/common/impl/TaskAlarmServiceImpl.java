package com.eghm.service.common.impl;

import cn.hutool.core.util.StrUtil;
import com.eghm.enums.EmailType;
import com.eghm.configuration.task.config.Task;
import com.eghm.dto.email.SendEmail;
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
    public void noticeAlarm(Task task, String errorMsg) {
        // 发送报警邮件通知
        if (StrUtil.isNotBlank(task.getAlarmEmail())) {
            SendEmail sendEmail = new SendEmail();
            sendEmail.setType(EmailType.TASK_ALARM);
            sendEmail.setEmail(task.getAlarmEmail());
            sendEmail.put("errorMsg", errorMsg);
            sendEmail.put("beanName", task.getBeanName());
            sendEmail.put("methodName", task.getMethodName());
            sendEmail.put("args", task.getArgs());
            emailService.sendEmail(sendEmail);
        }
    }
}
