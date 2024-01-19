package com.eghm.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.MemberQueryRequest;
import com.eghm.dto.member.log.LoginLogQueryRequest;
import com.eghm.model.LoginLog;
import com.eghm.service.member.LoginService;
import com.eghm.service.member.MemberService;
import com.eghm.utils.ExcelUtil;
import com.eghm.vo.member.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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
@RequestMapping("/manage/member")
public class MemberController {

    private final MemberService memberService;

    private final LoginService loginService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MemberResponse>> listPage(MemberQueryRequest request) {
        Page<MemberResponse> byPage = memberService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping("/freeze")
    @ApiOperation("冻结")
    public RespBody<Void> freeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), false);
        return RespBody.success();
    }

    @PostMapping("/unfreeze")
    @ApiOperation("解冻")
    public RespBody<Void> unfreeze(@Validated @RequestBody IdDTO dto) {
        memberService.updateState(dto.getId(), true);
        return RespBody.success();
    }

    @PostMapping("/offline")
    @ApiOperation("强制下线")
    public RespBody<Void> offline(@Validated @RequestBody IdDTO dto) {
        memberService.offline(dto.getId());
        return RespBody.success();
    }

    @GetMapping("/export")
    @ApiOperation("导出")
    public void export(HttpServletResponse response, MemberQueryRequest request) {
        List<MemberResponse> byPage = memberService.getList(request);
        ExcelUtil.export(response, "会员信息", byPage, MemberResponse.class);
    }

    @GetMapping("/loginPage")
    @ApiOperation("登录日志列表")
    public RespBody<PageData<LoginLog>> loginPage(LoginLogQueryRequest request) {
        Page<LoginLog> byPage = loginService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }
}
