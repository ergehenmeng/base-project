package com.eghm.dto.business.comment;

import com.eghm.annotation.Assign;
import com.eghm.enums.ObjectType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentDTO {

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 200, message = "评论内容最大200字符")
    @WordChecker(message = "评论内容包含敏感词汇")
    private String content;

    @Schema(description = "评论对象id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "评论对象id不能为空")
    private Long objectId;

    @Schema(description = "评论对象类型 (1:资讯 2:活动)", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "评论对象类型不能为空")
    private ObjectType objectType;

    @Schema(description = "父评论")
    private Long pid;

    @Schema(description = "评论id")
    private Long replyId;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
