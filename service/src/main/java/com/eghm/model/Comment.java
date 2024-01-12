package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Long commentId;

    @ApiModelProperty(value = "评论对象类型")
    private Integer commentType;

    @ApiModelProperty(value = "点赞数量")
    private Integer giveLike;

    @ApiModelProperty(value = "父评论ID")
    private Long pid;

    @ApiModelProperty(value = "评论信息")
    private String content;

}
