package com.eghm.web.controller.operate;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.PageData;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.comment.CommentQueryRequest;
import com.eghm.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.service.operate.CommentReportService;
import com.eghm.service.operate.CommentService;
import com.eghm.vo.operate.comment.CommentReportResponse;
import com.eghm.vo.operate.comment.CommentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Tag(name= "评论管理")
@AllArgsConstructor
@RequestMapping(value = "/manage/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    private final CommentReportService commentReportService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<CommentResponse>> listPage(CommentQueryRequest request) {
        Page<CommentResponse> byPage = commentService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/report/listPage")
    @Operation(summary = "举报列表")
    public RespBody<PageData<CommentReportResponse>> reportListPage(CommentReportQueryRequest request) {
        Page<CommentReportResponse> byPage = commentReportService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @PostMapping(value = "/shield", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "屏蔽")
    public RespBody<Void> shield(@Validated @RequestBody IdDTO dto) {
        commentService.updateState(dto.getId(), false);
        return RespBody.success();
    }

    @PostMapping(value = "/unShield", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "取消屏蔽")
    public RespBody<Void> unShield(@Validated @RequestBody IdDTO dto) {
        commentService.updateState(dto.getId(), true);
        return RespBody.success();
    }

    @PostMapping(value = "/top", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "置顶")
    public RespBody<Void> top(@Validated @RequestBody IdDTO dto) {
        commentService.updateTopState(dto.getId(), 1);
        return RespBody.success();
    }

    @PostMapping(value = "/unTop", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "取消置顶")
    public RespBody<Void> unTop(@Validated @RequestBody IdDTO dto) {
        commentService.updateTopState(dto.getId(), 0);
        return RespBody.success();
    }
}
