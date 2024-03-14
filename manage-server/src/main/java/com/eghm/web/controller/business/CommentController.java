package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.business.comment.CommentQueryRequest;
import com.eghm.dto.business.comment.CommentReportQueryRequest;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CommentReportService;
import com.eghm.service.business.CommentService;
import com.eghm.vo.business.comment.CommentReportResponse;
import com.eghm.vo.business.comment.CommentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Api(tags = "评论管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentReportService commentReportService;

    private final CommentService commentService;

    @GetMapping("/listPage")
    @ApiOperation("列表")
    public RespBody<PageData<CommentResponse>> listPage(CommentQueryRequest request) {
        Page<CommentResponse> byPage = commentService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/report/listPage")
    @ApiOperation("举报列表")
    public RespBody<PageData<CommentReportResponse>> reportListPage(CommentReportQueryRequest request) {
        Page<CommentReportResponse> byPage = commentReportService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/shield", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("屏蔽")
    public RespBody<Void> shield(@Validated @RequestBody IdDTO dto) {
        commentService.shield(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/top", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("置顶")
    public RespBody<Void> top(@Validated @RequestBody IdDTO dto) {
        commentService.updateTopState(dto.getId(), 1);
        return RespBody.success();
    }

    @PostMapping(value = "/unTop", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("取消置顶")
    public RespBody<Void> unTop(@Validated @RequestBody IdDTO dto) {
        commentService.updateTopState(dto.getId(), 0);
        return RespBody.success();
    }
}
