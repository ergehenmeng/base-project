package com.eghm.dto.business.comment;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.ObjectType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentDTO {

    @ApiModelProperty("评论内容")
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 200, message = "评论内容最大200字符")
    @WordChecker(message = "评论内容包含敏感词汇")
    private String content;

    @ApiModelProperty("评论对象id")
    @NotNull(message = "评论对象id不能为空")
    private Long objectId;

    @ApiModelProperty("评论对象类型")
    @NotNull(message = "评论对象类型不能为空")
    private ObjectType commentType;

    @ApiModelProperty("父评论")
    private Long pid;

    @ApiModelProperty("评论id")
    private Long replyId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
