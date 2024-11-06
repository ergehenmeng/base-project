package com.eghm.web.controller.sys;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.member.tag.*;
import com.eghm.model.MemberTag;
import com.eghm.service.member.MemberTagScopeService;
import com.eghm.service.member.MemberTagService;
import com.eghm.vo.member.MemberResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/3/6
 */

@RestController
@Api(tags = "会员标签")
@AllArgsConstructor
@RequestMapping(value = "/manage/member/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberTagController {

    private final MemberTagService memberTagService;

    private final MemberTagScopeService memberTagScopeService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<MemberTag>> listPage(MemberTagQueryRequest request) {
        Page<MemberTag> byPage = memberTagService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("新增")
    public RespBody<Void> create(@Validated @RequestBody MemberTagAddRequest request) {
        memberTagService.create(request);
        return RespBody.success();
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("更新")
    public RespBody<Void> update(@Validated @RequestBody MemberTagEditRequest request) {
        memberTagService.update(request);
        return RespBody.success();
    }

    @PostMapping(value = "/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("刷新")
    public RespBody<Void> refresh(@Validated @RequestBody IdDTO request) {
        memberTagService.refresh(request.getId());
        return RespBody.success();
    }

    @GetMapping("/select")
    @ApiOperation("详情")
    public RespBody<MemberTag> select(@Validated IdDTO request) {
        MemberTag memberTag = memberTagService.selectByIdRequired(request.getId());
        return RespBody.success(memberTag);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除")
    public RespBody<Void> delete(@Validated @RequestBody IdDTO request) {
        memberTagService.delete(request.getId());
        return RespBody.success();
    }

    @GetMapping("/memberPage")
    @ApiOperation("对应标签会员列表")
    public RespBody<PageData<MemberResponse>> memberPage(@Validated TagMemberQueryRequest request) {
        Page<MemberResponse> byPage = memberTagScopeService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/sendNotice", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("发送通知")
    public RespBody<Void> sendNotice(@Validated @RequestBody SendNotifyRequest request) {
        memberTagScopeService.sendNotice(request);
        return RespBody.success();
    }

    @PostMapping(value = "/sendSms", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("发送短信")
    public RespBody<Void> sendSms(@Validated @RequestBody SendSmsRequest request) {
        memberTagScopeService.sendSms(request);
        return RespBody.success();
    }
}