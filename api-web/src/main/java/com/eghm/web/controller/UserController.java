package com.eghm.web.controller;

import com.eghm.model.dto.ext.ApiHolder;
import com.eghm.model.dto.ext.RespBody;
import com.eghm.model.dto.user.BindEmailDTO;
import com.eghm.model.dto.user.ChangeEmailDTO;
import com.eghm.model.dto.user.SendEmailAuthCodeDTO;
import com.eghm.model.vo.user.SignInVO;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/setPwd")
    @ApiOperation("设置新密码")
    public RespBody<Void> setPwd(){
        return RespBody.success();
    }

    /**
     * 绑定邮箱发送邮箱验证码 ❶
     */
    @PostMapping("/sendBindEmailCode")
    @ApiOperation("绑定邮箱发送验证码请求")
    public RespBody<Void> sendBindEmail(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        userService.sendBindEmail(request.getEmail(), ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 绑定邮箱 目前绑定邮箱不需要短信二次校验,后期可以改为先短信校验,再邮箱校验 ❷
     */
    @PostMapping("/bindEmail")
    @ApiOperation("首次绑定邮箱")
    public RespBody<Void> bindEmail(@RequestBody @Validated BindEmailDTO request) {
        request.setUserId(ApiHolder.getUserId());
        userService.bindEmail(request);
        return RespBody.success();
    }

    /**
     * 更新邮箱时,需要短信验证码,因此此时必须绑定手机号码 ①
     */
    @PostMapping("/sendChangeEmailSms")
    @ApiOperation("发送换绑邮箱的短信验证码")
    public RespBody<Void> sendChangeEmailSms() {
        userService.sendChangeEmailSms(ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 更新邮箱时,新邮箱地址需要验证码确认 ②
     */
    @PostMapping("/sendChangeEmailCode")
    @ApiOperation("发送换绑邮箱的邮箱验证码")
    public RespBody<Void> sendChangeEmailCode(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        userService.sendChangeEmailCode(request);
        return RespBody.success();
    }

    /**
     * 绑定新邮箱账号 ③
     */
    @PostMapping("/bindChangeEmail")
    @ApiOperation("绑定新邮箱账号")
    public RespBody<Void> bindChangeEmail(@RequestBody @Validated ChangeEmailDTO request) {
        userService.changeEmail(request);
        return RespBody.success();
    }

    /**
     * 用户签到
     */
    @PostMapping("/signIn")
    @ApiOperation("用户签到")
    public RespBody<Void> signIn() {
        userService.signIn(ApiHolder.getUserId());
        return RespBody.success();
    }

    /**
     * 查询用户签到信息
     */
    @GetMapping("/getSignIn")
    @ApiOperation("获取用户签到信息")
    public RespBody<SignInVO> getSignIn() {
        SignInVO signIn = userService.getSignIn(ApiHolder.getUserId());
        return RespBody.success(signIn);
    }

}
