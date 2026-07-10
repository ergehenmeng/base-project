package com.eghm.interfaces.webapp.controller.business;

import com.eghm.application.shared.dto.IdDTO;
import com.eghm.application.shared.configuration.authentication.ApiHolder;
import com.eghm.application.shared.dto.ext.RespBody;
import com.eghm.application.shared.dto.operate.comment.CommentDTO;
import com.eghm.application.shared.dto.operate.comment.CommentQueryDTO;
import com.eghm.application.shared.dto.operate.comment.CommentReportDTO;
import com.eghm.application.operate.query.CommentQueryService;
import com.eghm.application.operate.service.CommentReportApplicationService;
import com.eghm.application.operate.service.CommentApplicationService;
import com.eghm.application.shared.vo.operate.comment.CommentSecondVO;
import com.eghm.application.shared.vo.operate.comment.CommentVO;
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
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Tag(name = "评论留言")
@AllArgsConstructor
@RequestMapping(value = "/webapp/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentApplicationService commentService;

    private final CommentQueryService commentQueryService;

    private final CommentReportApplicationService commentReportService;

    @GetMapping("/listPage")
    @Operation(summary = "评论列表")
    public RespBody<List<CommentVO>> getByPage(@ParameterObject @Validated CommentQueryDTO dto) {
        return RespBody.success(commentQueryService.listClientPage(dto));
    }

    @GetMapping("/secondPage")
    @Operation(summary = "二级评论")
    public RespBody<List<CommentSecondVO>> secondPage(@ParameterObject @Validated CommentQueryDTO dto) {
        return RespBody.success(commentQueryService.listSecondClientPage(dto));
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "添加评论")
    @AccessToken
    public RespBody<Void> add(@Validated @RequestBody CommentDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        commentService.add(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "删除评论")
    @AccessToken
    public RespBody<Void> add(@Validated @RequestBody IdDTO dto) {
        commentService.delete(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/praise", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "点赞")
    @AccessToken
    public RespBody<Void> praise(@Validated @RequestBody IdDTO dto) {
        commentService.praise(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "举报评论")
    @AccessToken
    public RespBody<Void> report(@Validated @RequestBody CommentReportDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        commentReportService.report(dto);
        return RespBody.success();
    }

}
