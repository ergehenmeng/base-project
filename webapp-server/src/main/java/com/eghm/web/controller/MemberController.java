package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.BindEmailDTO;
import com.eghm.dto.member.ChangeEmailDTO;
import com.eghm.dto.member.SendEmailAuthCodeDTO;
import com.eghm.service.member.MemberService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.member.SignInVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 */
@AccessToken
@RestController
@Api(tags = "用户相关接口")
@AllArgsConstructor
@RequestMapping("/webapp/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sendBindEmailCode")
    @ApiOperation("绑定邮箱发送验证码请求①")
    public RespBody<Void> sendBindEmail(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        memberService.sendBindEmail(request.getEmail(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping("/bindEmail")
    @ApiOperation("首次绑定邮箱②")
    public RespBody<Void> bindEmail(@RequestBody @Validated BindEmailDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberService.bindEmail(request);
        return RespBody.success();
    }

    @PostMapping("/sendChangeEmailSms")
    @ApiOperation("发送换绑邮箱的短信验证码①")
    public RespBody<Void> sendChangeEmailSms(HttpServletRequest request) {
        memberService.sendChangeEmailSms(ApiHolder.getMemberId(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping("/sendChangeEmailCode")
    @ApiOperation("发送换绑邮箱的邮箱验证码②")
    public RespBody<Void> sendChangeEmailCode(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        memberService.sendChangeEmailCode(request);
        return RespBody.success();
    }

    @PostMapping("/bindChangeEmail")
    @ApiOperation("绑定新邮箱账号③")
    public RespBody<Void> bindChangeEmail(@RequestBody @Validated ChangeEmailDTO request) {
        memberService.changeEmail(request);
        return RespBody.success();
    }

    @PostMapping("/signIn")
    @ApiOperation("用户签到")
    public RespBody<Void> signIn() {
        memberService.signIn(ApiHolder.getMemberId());
        return RespBody.success();
    }

    @GetMapping("/getSignIn")
    @ApiOperation("获取用户签到信息")
    public RespBody<SignInVO> getSignIn() {
        SignInVO signIn = memberService.getSignIn(ApiHolder.getMemberId());
        return RespBody.success(signIn);
    }

}
