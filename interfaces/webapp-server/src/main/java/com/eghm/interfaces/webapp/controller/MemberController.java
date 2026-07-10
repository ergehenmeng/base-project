package com.eghm.interfaces.webapp.controller;

import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.business.member.BindEmailDTO;
import com.eghm.application.shared.dto.business.member.ChangeEmailDTO;
import com.eghm.application.shared.dto.business.member.MemberDTO;
import com.eghm.application.shared.dto.business.member.SendEmailAuthCodeDTO;
import com.eghm.application.member.query.MemberInviteLogQueryService;
import com.eghm.application.member.query.MemberNoticeQueryService;
import com.eghm.application.member.service.MemberApplicationService;
import com.eghm.interfaces.core.utils.IpUtil;
import com.eghm.application.shared.vo.business.member.MemberInviteVO;
import com.eghm.application.shared.vo.business.member.MemberVO;
import com.eghm.application.shared.vo.business.member.SignInVO;
import com.eghm.interfaces.webapp.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 */
@AccessToken
@RestController
@Tag(name = "用户相关接口")
@AllArgsConstructor
@RequestMapping(value = "/webapp/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberApplicationService memberService;

    private final MemberNoticeQueryService memberNoticeQueryService;

    private final MemberInviteLogQueryService memberInviteLogQueryService;

    @PostMapping(value = "/sendBindEmailCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "绑定邮箱发送验证码请求①")
    public RespBody<Void> sendBindEmail(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        memberService.sendBindEmail(request.getEmail(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/bindEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "首次绑定邮箱②")
    public RespBody<Void> bindEmail(@RequestBody @Validated BindEmailDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberService.bindEmail(request);
        return RespBody.success();
    }

    @PostMapping(value = "/sendChangeEmailSms")
    @Operation(summary = "发送换绑邮箱的短信验证码①")
    public RespBody<Void> sendChangeEmailSms(HttpServletRequest request) {
        memberService.sendChangeEmailSms(ApiHolder.getMemberId(), IpUtil.getIpAddress(request));
        return RespBody.success();
    }

    @PostMapping(value = "/sendChangeEmailCode", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送换绑邮箱的邮箱验证码②")
    public RespBody<Void> sendChangeEmailCode(@RequestBody @Validated SendEmailAuthCodeDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberService.sendChangeEmailCode(request);
        return RespBody.success();
    }

    @PostMapping(value = "/bindChangeEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "绑定新邮箱账号③")
    public RespBody<Void> bindChangeEmail(@RequestBody @Validated ChangeEmailDTO request) {
        request.setMemberId(ApiHolder.getMemberId());
        memberService.changeEmail(request);
        return RespBody.success();
    }

    @GetMapping("/my")
    @Operation(summary = "我的")
    public RespBody<MemberVO> my() {
        MemberVO vo = memberService.memberHome(ApiHolder.getMemberId());
        Long unRead = memberNoticeQueryService.countUnRead(ApiHolder.getMemberId());
        vo.setUnRead(unRead);
        return RespBody.success(vo);
    }

    @PostMapping("/signIn")
    @Operation(summary = "用户签到")
    public RespBody<Void> signIn() {
        memberService.signIn(ApiHolder.getMemberId());
        return RespBody.success();
    }

    @GetMapping("/getSignIn")
    @Operation(summary = "获取用户签到信息")
    public RespBody<SignInVO> getSignIn() {
        SignInVO signIn = memberService.getSignIn(ApiHolder.getMemberId());
        return RespBody.success(signIn);
    }

    @PostMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "编辑保存会员信息")
    public RespBody<Void> edit(@RequestBody @Validated MemberDTO dto) {
        memberService.edit(ApiHolder.getMemberId(), dto);
        return RespBody.success();
    }

    @GetMapping("/invitePage")
    @Operation(summary = "邀请记录")
    public RespBody<List<MemberInviteVO>> invitePage(@ParameterObject PagingQuery query) {
        List<MemberInviteVO> byPage = memberInviteLogQueryService.getByPage(query.createPage(false), ApiHolder.getMemberId());
        return RespBody.success(byPage);
    }
}
