package com.eghm.web.controller.business;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Api(tags = "评论管理")
@AllArgsConstructor
@RequestMapping("/webapp/comment")
public class CommentController {

    private final CommentReportService commentReportService;

    private final CommentService commentService;

    @GetMapping("/listPage")
    @ApiOperation("评论列表")
    public RespBody<PageData<CommentResponse>> listPage(CommentQueryRequest request) {
        Page<CommentResponse> byPage = commentService.listPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

    @GetMapping("/report/listPage")
    @ApiOperation("举报评论列表")
    public RespBody<PageData<CommentReportResponse>> reportListPage(CommentReportQueryRequest request) {
        Page<CommentReportResponse> byPage = commentReportService.getByPage(request);
        return RespBody.success(PageData.toPage(byPage));
    }

}
