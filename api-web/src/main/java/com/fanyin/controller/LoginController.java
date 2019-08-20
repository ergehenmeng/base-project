package com.fanyin.controller;

import com.fanyin.model.dto.login.SendSmsCode;
import com.fanyin.model.ext.RespBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆,找回密码相关
 * @author 二哥很猛
 * @date 2019/8/20 10:17
 */
@RestController
public class LoginController extends AbstractController{



    @RequestMapping("/login/send_sms")
    public RespBody sendSms(SendSmsCode code){
        return RespBody.getInstance();
    }

}
