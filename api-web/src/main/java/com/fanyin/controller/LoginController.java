package com.fanyin.controller;

import com.fanyin.model.dto.login.SendSmsCode;
import com.fanyin.model.ext.RespBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆,找回密码相关
 * @author 二哥很猛
 * @date 2019/8/20 10:17
 */
@RestController
@Api(tags = "登陆,找回密码功能")
public class LoginController extends AbstractController{


    /**
     * 登陆发送短信验证码
     */
    @ApiOperation(value = "短信登陆发送验证码")
    @PostMapping("/login/send_sms")
    public RespBody sendSms(SendSmsCode code){
        return RespBody.getInstance();
    }

}
