package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ObjectType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "评论对象ID")
    private Long objectId;

    @ApiModelProperty("状态 0:已屏蔽 1:正常")
    private Boolean state;

    @ApiModelProperty("置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @ApiModelProperty(value = "评论对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNum;

    @ApiModelProperty("回复id")
    private Long replyId;

    @ApiModelProperty(value = "评论信息")
    private String content;

    @ApiModelProperty("被举报次数")
    private Integer reportNum;

    @ApiModelProperty("父评论")
    private Long pid;

    @ApiModelProperty("评论级别 1:一级评论 2:二级评论")
    private Integer grade;

    @ApiModelProperty("被评论次数")
    private Integer replyNum;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
}
