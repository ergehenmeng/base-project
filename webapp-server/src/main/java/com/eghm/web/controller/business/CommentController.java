package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.dto.operate.comment.CommentDTO;
import com.eghm.dto.operate.comment.CommentQueryDTO;
import com.eghm.dto.operate.comment.CommentReportDTO;
import com.eghm.service.operate.CommentReportService;
import com.eghm.service.operate.CommentService;
import com.eghm.vo.operate.comment.CommentSecondVO;
import com.eghm.vo.operate.comment.CommentVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Tag(name= "评论留言")
@AllArgsConstructor
@RequestMapping(value = "/webapp/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    private final CommentReportService commentReportService;

    @GetMapping("/listPage")
    @Operation(summary = "评论列表")
    public RespBody<List<CommentVO>> getByPage(@Validated CommentQueryDTO dto) {
        return RespBody.success(commentService.getByPage(dto));
    }

    @GetMapping("/secondPage")
    @Operation(summary = "二级评论")
    public RespBody<List<CommentSecondVO>> secondPage(@Validated CommentQueryDTO dto) {
        return RespBody.success(commentService.secondPage(dto));
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
