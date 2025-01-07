package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ObjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 评论记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment")
public class Comment extends BaseEntity {

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "评论对象ID")
    private Long objectId;

    @Schema(description = "状态 0:已屏蔽 1:正常")
    private Boolean state;

    @Schema(description = "置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @Schema(description = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "回复id")
    private Long replyId;

    @Schema(description = "评论信息")
    private String content;

    @Schema(description = "被举报次数")
    private Integer reportNum;

    @Schema(description = "父评论")
    private Long pid;

    @Schema(description = "评论级别 1:一级评论 2:二级评论")
    private Integer grade;

    @Schema(description = "被评论次数")
    private Integer replyNum;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
}
