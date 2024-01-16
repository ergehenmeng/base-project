package com.eghm.web.controller.business;

import com.eghm.dto.IdDTO;
import com.eghm.dto.business.comment.CommentDTO;
import com.eghm.dto.business.comment.CommentQueryDTO;
import com.eghm.dto.ext.RespBody;
import com.eghm.service.business.CommentService;
import com.eghm.vo.business.comment.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@RestController
@Api(tags = "评论留言")
@AllArgsConstructor
@RequestMapping("/webapp/comment")
public class CommentController {

    private final CommentService commentService;

    @RequestMapping("/listPage")
    @ApiOperation("评论列表")
    public RespBody<List<CommentVO>> getByPage(CommentQueryDTO dto) {
        return RespBody.success(commentService.getByPage(dto));
    }

    @RequestMapping("/add")
    @ApiOperation("添加评论")
    public RespBody<Void> add(@Validated @RequestBody CommentDTO dto) {
        commentService.add(dto);
        return RespBody.success();
    }

    @RequestMapping("/giveLike")
    @ApiOperation("点赞")
    public RespBody<Void> giveLike(@Validated @RequestBody IdDTO dto) {
        commentService.giveLike(dto.getId());
        return RespBody.success();
    }

}
