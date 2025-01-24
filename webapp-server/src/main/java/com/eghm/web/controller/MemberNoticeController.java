package com.eghm.web.controller;


import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.MemberNoticeService;
import com.eghm.vo.business.member.MemberNoticeVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 */
@AccessToken
@RestController
@Tag(name = "站内信")
@AllArgsConstructor
@RequestMapping(value = "/webapp/member/notice", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberNoticeController {

    private final MemberNoticeService memberNoticeService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<List<MemberNoticeVO>> listPage(@ParameterObject PagingQuery query) {
        List<MemberNoticeVO> paging = memberNoticeService.getByPage(query, ApiHolder.getMemberId());
        return RespBody.success(paging);
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除站内信")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        memberNoticeService.deleteNotice(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/markRead", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "设置消息已读(消息未读时才调用)")
    public RespBody<Void> markRead(@RequestBody @Validated IdDTO dto) {
        memberNoticeService.setNoticeRead(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

}
