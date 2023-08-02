package com.eghm.web.controller;


import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.member.MemberNoticeService;
import com.eghm.vo.member.MemberNoticeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 殿小二
 */
@RestController
@Api(tags = "站内信")
@AllArgsConstructor
@RequestMapping("/webapp/member/notice")
public class MemberNoticeController {

    private final MemberNoticeService memberNoticeService;

    @GetMapping("/listPage")
    @ApiOperation("分页查询站内信列表")
    public RespBody<List<MemberNoticeVO>> listPage(PagingQuery query) {
        List<MemberNoticeVO> paging = memberNoticeService.getByPage(query, ApiHolder.getMemberId());
        return RespBody.success(paging);
    }

    @PostMapping("/delete")
    @ApiOperation("删除站内信")
    public RespBody<Void> delete(@RequestBody @Validated IdDTO dto) {
        memberNoticeService.deleteNotice(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping("/markRead")
    @ApiOperation("设置消息已读(消息未读时才调用)")
    public RespBody<Void> markRead(@RequestBody @Validated IdDTO dto) {
        memberNoticeService.setNoticeRead(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

}
