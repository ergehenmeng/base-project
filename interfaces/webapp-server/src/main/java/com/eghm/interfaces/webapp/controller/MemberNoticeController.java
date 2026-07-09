package com.eghm.interfaces.webapp.controller;


import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.PagingQuery;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.member.port.in.MemberNoticeService;
import com.eghm.application.shared.vo.business.member.MemberNoticeVO;
import com.eghm.interfaces.webapp.annotation.AccessToken;
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
