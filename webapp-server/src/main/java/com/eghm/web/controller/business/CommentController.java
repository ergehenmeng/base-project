package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
import com.eghm.dto.business.comment.CommentReportDTO;
import com.eghm.dto.ext.ApiHolder;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CommentReportService;
import com.eghm.service.business.CommentService;
import com.eghm.vo.business.comment.CommentSecondVO;
import com.eghm.vo.business.comment.CommentVO;
import com.eghm.web.annotation.AccessToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "评论留言")
@AllArgsConstructor
@RequestMapping(value = "/webapp/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;

    private final CommentReportService commentReportService;

    @GetMapping("/listPage")
    @ApiOperation("评论列表")
    public RespBody<List<CommentVO>> getByPage(@Validated CommentQueryDTO dto) {
        return RespBody.success(commentService.getByPage(dto));
    }

    @GetMapping("/secondPage")
    @ApiOperation("二级评论")
    public RespBody<List<CommentSecondVO>> secondPage(@Validated CommentQueryDTO dto) {
        return RespBody.success(commentService.secondPage(dto));
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("添加评论")
    @AccessToken
    public RespBody<Void> add(@Validated @RequestBody CommentDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        commentService.add(dto);
        return RespBody.success();
    }

    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("删除评论")
    @AccessToken
    public RespBody<Void> add(@Validated @RequestBody IdDTO dto) {
        commentService.delete(dto.getId(), ApiHolder.getMemberId());
        return RespBody.success();
    }

    @PostMapping(value = "/praise", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("点赞")
    @AccessToken
    public RespBody<Void> praise(@Validated @RequestBody IdDTO dto) {
        commentService.praise(dto.getId());
        return RespBody.success();
    }

    @PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("举报评论")
    @AccessToken
    public RespBody<Void> report(@Validated @RequestBody CommentReportDTO dto) {
        dto.setMemberId(ApiHolder.getMemberId());
        commentReportService.report(dto);
        return RespBody.success();
    }

}
