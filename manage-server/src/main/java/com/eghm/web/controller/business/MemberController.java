package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.member.*;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.enums.MemberState;
import com.eghm.model.LoginLog;
import com.eghm.service.business.LoginService;
import com.eghm.service.business.MemberNoticeService;
import com.eghm.service.business.MemberScoreLogService;
import com.eghm.service.business.MemberService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.business.member.MemberResponse;
import com.eghm.vo.business.member.MemberScoreVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/18 14:16
 */
@RestController
@Tag(name = "会员信息")
@AllArgsConstructor
@RequestMapping(value = "/manage/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final LoginService loginService;

    private final MemberService memberService;

    private final MemberNoticeService memberNoticeService;

    private final MemberScoreLogService memberScoreLogService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<MemberResponse>> listPage(@ParameterObject MemberQueryRequest request) {
        Page<MemberResponse> byPage = memberService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @PostMapping(value = "/freeze", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "冻结")
    public RespBody<Void> freeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), MemberState.FREEZE);
        return RespBody.success();
    }

    @PostMapping(value = "/unfreeze", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "解冻")
    public RespBody<Void> unfreeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), MemberState.NORMAL);
        return RespBody.success();
    }

    @PostMapping(value = "/offline", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "强制下线")
    public RespBody<Void> offline(@Validated @RequestBody IdDTO dto) {
        memberService.offline(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @Operation(summary = "导出")
    public void export(HttpServletResponse response, @ParameterObject MemberQueryRequest request) {
        List<MemberResponse> byPage = memberService.getList(request);
        EasyExcelUtil.export(response, "会员信息", byPage, MemberResponse.class);
    }

    @GetMapping("/loginPage")
    @Operation(summary = "登录日志列表")
    public RespBody<PageData<LoginLog>> loginPage(@ParameterObject @Validated LoginLogQueryRequest request) {
        Page<LoginLog> byPage = loginService.getByPage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @PostMapping(value = "/sendNotice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送通知")
    public RespBody<Void> sendNotice(@Validated @RequestBody SendNotifyRequest request) {
        memberNoticeService.sendNoticeBatch(request);
        return RespBody.success();
    }

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "发送消息")
    public RespBody<Void> sendSms(@Validated @RequestBody SendSmsRequest request) {
        memberService.sendSms(request);
        return RespBody.success();
    }

    @PostMapping(value = "/updateScore", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "更新积分")
    public RespBody<Void> updateScore(@Validated @RequestBody ScoreUpdateRequest request) {
        memberService.updateScore(request.getId(), request.getScoreType(), request.getScore(), request.getRemark());
        return RespBody.success();
    }

    @GetMapping("/score/listPage")
    @Operation(summary = "积分列表")
    public RespBody<PageData<MemberScoreVO>> listPage(@Validated @ParameterObject MemberScoreQueryRequest request) {
        Page<MemberScoreVO> page = memberScoreLogService.getByPage(request);
        return RespBody.success(PageData.convert(page));
    }
}
