package com.eghm.controller;

import com.eghm.model.dto.email.EmailSendCodeRequest;
import com.eghm.model.ext.RespBody;
import com.eghm.service.common.EmailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 殿小二
 * @date 2020/8/28
 */
@RestController
@RequestMapping("/api")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email/send_code")
    @ApiOperation("发送邮箱验证码")
    public RespBody<Object> sendCode(EmailSendCodeRequest request) {
        return RespBody.success();
    }

}
