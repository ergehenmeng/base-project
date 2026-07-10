package com.eghm.interfaces.manage.controller.operate;

import com.eghm.application.shared.dto.ext.Page;
import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.dto.ext.PageData;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.comment.CommentQueryRequest;
import com.eghm.application.shared.dto.operate.comment.CommentReportQueryRequest;
import com.eghm.application.operate.query.CommentQueryService;
import com.eghm.application.operate.query.CommentReportQueryService;
import com.eghm.application.operate.service.CommentApplicationService;
import com.eghm.application.shared.vo.operate.comment.CommentReportResponse;
import com.eghm.application.shared.vo.operate.comment.CommentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@AllArgsConstructor
@Tag(name = "评论管理")
@RequestMapping(value = "/manage/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentApplicationService commentService;

    private final CommentQueryService commentQueryService;

    private final CommentReportQueryService commentReportQueryService;

    @GetMapping("/listPage")
    @Operation(summary = "列表")
    public RespBody<PageData<CommentResponse>> listPage(@ParameterObject CommentQueryRequest request) {
        Page<CommentResponse> byPage = commentQueryService.listManagePage(request);
        return RespBody.success(PageData.convert(byPage));
    }

    @GetMapping("/report/listPage")
    @Operation(summary = "举报列表")
    public RespBody<PageData<CommentReportResponse>> reportListPage(@ParameterObject CommentReportQueryRequest request) {
        Page<CommentReportResponse> byPage = commentReportQueryService.getByPage(request.createPage(), request);
        return RespBody.success(PageData.convert(byPage));
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
