package com.eghm.vo.operate.comment;

import com.eghm.convertor.DateParseSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentSecondVO {

    @Schema(description = "留言id")
    private Long id;

    @Schema(description = "父评论ID")
    private Long pid;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "评论信息")
    private String content;

    @Schema(description = "评论时间")
    @JsonSerialize(using = DateParseSerializer.class)
    private LocalDateTime createTime;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "回复id")
    private Long replyId;

    @Schema(description = "被评论次数")
    private Integer replyNum;

    @Schema(description = "置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @Schema(description = "被回复的评论是否删除")
    private Boolean replyDeleted;

    @Schema(description = "被回复的评论是否屏蔽")
    private Boolean replyState;

    @Schema(description = "回复昵称")
    private String replyNickName;

    @Schema(description = "回复头像")
    private String replyAvatar;

    @Schema(description = "回复内容")
    private String replyContent;

    @Schema(description = "是否已点赞")
    private Boolean hasPraise;
}
