package com.eghm.web.controller;

import com.eghm.model.dto.user.BindEmailDTO;
import com.eghm.model.dto.user.ChangeEmailDTO;
import com.eghm.model.dto.user.SendEmailAuthCodeDTO;
import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.service.user.UserService;
import com.eghm.web.annotation.SkipLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 */
@RestController
@Api(tags = "用户相关接口")
public class UserController {

    private UserService userService;

    @Autowired
    @SkipLogger
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/setup_password")
    public RespBody<Object> setupPassword(){
        return RespBody.success();
    }

    /**
     * 绑定邮箱发送邮箱验证码 ❶
     */
    @PostMapping("/user/send_bind_email_code")
    @ApiOperation("绑定邮箱发送验证码请求")
    public RespBody<Object> sendBindEmail(SendEmailAuthCodeDTO request) {
        userService.sendBindEmail(request.getEmail(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 绑定邮箱 目前绑定邮箱不需要短信二次校验,后期可以改为先短信校验,再邮箱校验 ❷
     */
    @PostMapping("/user/bind_email")
    @ApiOperation("首次绑定邮箱")
    public RespBody<Object> bindEmail(BindEmailDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userService.bindEmail(request);
        return RespBody.success();
    }

    /**
     * 更新邮箱时,需要短信验证码,因此此时必须绑定手机号码 ①
     */
    @PostMapping("/user/send_change_email_sms")
    @ApiOperation("发送换绑邮箱的短信验证码")
    public RespBody<Object> sendChangeEmailSms() {
        userService.sendChangeEmailSms(ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 更新邮箱时,新邮箱地址需要验证码确认 ②
     */
    @PostMapping("/user/send_change_email_code")
    @ApiOperation("发送换绑邮箱的邮箱验证码")
    public RespBody<Object> sendChangeEmailCode(SendEmailAuthCodeDTO request) {
        userService.sendChangeEmailCode(request);
        return RespBody.success();
    }

    /**
     * 绑定新邮箱账号 ③
     */
    @PostMapping("/user/bind_change_email")
    @ApiOperation("绑定新邮箱账号")
    public RespBody<Object> bindChangeEmail(ChangeEmailDTO request) {
        userService.changeEmail(request);
        return RespBody.success();
    }

    /**
     * 用户签到
     */
    @PostMapping("/user/sign_in")
    @ApiOperation("用户签到")
    public RespBody<Object> signIn() {
        userService.signIn(ApiHolder.getUserId());
        return RespBody.success();
    }

}
