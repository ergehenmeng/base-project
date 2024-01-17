package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ObjectType;
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

    @ApiModelProperty(value = "评论对象类型")
    private ObjectType commentType;

    @ApiModelProperty(value = "点赞数量")
    private Integer likeNum;

    @ApiModelProperty("回复id")
    private Long replyId;

    @ApiModelProperty(value = "评论信息")
    private String content;

    @ApiModelProperty("被举报次数")
    private Integer reportNum;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "MM-dd HH:mm")
    private LocalDateTime createTime;
}
