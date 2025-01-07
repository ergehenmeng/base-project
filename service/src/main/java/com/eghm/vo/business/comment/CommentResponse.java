package com.eghm.vo.business.comment;

import com.eghm.enums.ref.ObjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentResponse {

    @Schema(description = "留言id")
    private Long id;

    @Schema(description = "评论对象标题")
    private String objectName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "状态 0:已屏蔽 1:正常")
    private Boolean state;

    @Schema(description = "置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @Schema(description = "评论对象id")
    private Long objectId;

    @Schema(description = "被举报次数")
    private Integer reportNum;

    @Schema(description = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "评论信息")
    private String content;

    @Schema(description = "评论时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

}
