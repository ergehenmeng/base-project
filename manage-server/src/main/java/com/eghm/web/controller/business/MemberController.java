package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.business.member.MemberQueryRequest;
import com.eghm.dto.business.member.LoginLogQueryRequest;
import com.eghm.dto.business.member.SendNotifyRequest;
import com.eghm.model.LoginLog;
import com.eghm.service.business.LoginService;
import com.eghm.service.business.MemberNoticeService;
import com.eghm.service.business.MemberService;
import com.eghm.utils.EasyExcelUtil;
import com.eghm.vo.member.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 二哥很猛
 * @since 2023/8/18 14:16
 */
@RestController
@Api(tags = "会员信息")
@AllArgsConstructor
@RequestMapping(value = "/manage/member", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberController {

    private final LoginService loginService;

    private final MemberService memberService;

    private final MemberNoticeService memberNoticeService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MemberResponse>> listPage(MemberQueryRequest request) {
        Page<MemberResponse> byPage = memberService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/freeze", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("冻结")
    public RespBody<Void> freeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), false);
        return RespBody.success();
    }

    @PostMapping(value = "/unfreeze", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("解冻")
    public RespBody<Void> unfreeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), true);
        return RespBody.success();
    }

    @PostMapping(value = "/offline", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("强制下线")
    public RespBody<Void> offline(@Validated @RequestBody IdDTO dto) {
        memberService.offline(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, MemberQueryRequest request) {
        List<MemberResponse> byPage = memberService.getList(request);
        EasyExcelUtil.export(response, "会员信息", byPage, MemberResponse.class);
    }

    @GetMapping("/loginPage")
    @ApiOperation("登录日志列表")
    public RespBody<PageData<LoginLog>> loginPage(@Validated LoginLogQueryRequest request) {
        Page<LoginLog> byPage = loginService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/sendNotice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("发送通知")
    public RespBody<Void> sendNotice(@Validated @RequestBody SendNotifyRequest request) {
        memberNoticeService.sendNoticeBatch(request);
        return RespBody.success();
    }

}
