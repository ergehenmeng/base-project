package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.user.BindEmailDTO;
import com.eghm.dto.user.ChangeEmailDTO;
import com.eghm.dto.user.SendEmailAuthCodeDTO;
import com.eghm.vo.user.SignInVO;
import com.eghm.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 */
@RestController
@Api(tags = "用户相关接口")
@AllArgsConstructor
@RequestMapping("/webapp/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/setPwd")
    @ApiOperation("设置新密码")
    public RespBody<Void> setPwd(){
        return RespBody.success();
    }

    @PostMapping("/sendBindEmailCode")
    @ApiOperation("绑定邮箱发送验证码请求①")
    public RespBody<Void> sendBindEmail(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        userService.sendBindEmail(request.getEmail(), ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/bindEmail")
    @ApiOperation("首次绑定邮箱②")
    public RespBody<Void> bindEmail(@RequestBody @Validated BindEmailDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userService.bindEmail(request);
        return RespBody.success();
    }

    @PostMapping("/sendChangeEmailSms")
    @ApiOperation("发送换绑邮箱的短信验证码①")
    public RespBody<Void> sendChangeEmailSms() {
        userService.sendChangeEmailSms(ApiHolder.getUserId());
        return RespBody.success();
    }

    @PostMapping("/sendChangeEmailCode")
    @ApiOperation("发送换绑邮箱的邮箱验证码②")
    public RespBody<Void> sendChangeEmailCode(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        userService.sendChangeEmailCode(request);
        return RespBody.success();
    }

    @PostMapping("/bindChangeEmail")
    @ApiOperation("绑定新邮箱账号③")
    public RespBody<Void> bindChangeEmail(@RequestBody @Validated ChangeEmailDTO request) {
        userService.changeEmail(request);
        return RespBody.success();
    }

    @PostMapping("/signIn")
    @ApiOperation("用户签到")
    public RespBody<Void> signIn() {
        userService.signIn(ApiHolder.getUserId());
        return RespBody.success();
    }

    @GetMapping("/getSignIn")
    @ApiOperation("获取用户签到信息")
    public RespBody<SignInVO> getSignIn() {
        SignInVO signIn = userService.getSignIn(ApiHolder.getUserId());
        return RespBody.success(signIn);
    }

}
