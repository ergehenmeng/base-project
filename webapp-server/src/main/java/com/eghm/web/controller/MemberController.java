package com.eghm.web.controller;

import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.BindEmailDTO;
import com.eghm.dto.member.ChangeEmailDTO;
import com.eghm.dto.member.MemberDTO;
import com.eghm.dto.member.SendEmailAuthCodeDTO;
import com.eghm.enums.VisitType;
import com.eghm.service.member.MemberInviteLogService;
import com.eghm.service.member.MemberNoticeService;
import com.eghm.service.member.MemberService;
import com.eghm.utils.IpUtil;
import com.eghm.vo.member.MemberInviteVO;
import com.eghm.vo.member.MemberVO;
import com.eghm.vo.member.SignInVO;
import com.eghm.web.annotation.AccessToken;
import com.eghm.web.annotation.VisitRecord;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关信息
 *
 * @author 二哥很猛
 */
@AccessToken
@RestController
@Tag(name="用户相关接口")
@AllArgsConstructor
@RequestMapping(value = "/webapp/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final MemberService memberService;

    private final MemberNoticeService memberNoticeService;

    private final MemberInviteLogService memberInviteLogService;

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

    @GetMapping("/my")
    @Operation(summary = "我的")
    @VisitRecord(VisitType.SELF_CENTER)
    public RespBody<MemberVO> my() {
        MemberVO vo = memberService.memberHome(ApiHolder.getMemberId());
        Long unRead = memberNoticeService.countUnRead(ApiHolder.getMemberId());
        vo.setUnRead(unRead);
        return RespBody.success(vo);
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
        List<MemberInviteVO> byPage = memberInviteLogService.getByPage(query, ApiHolder.getMemberId());
        return RespBody.success(byPage);
    }
}
