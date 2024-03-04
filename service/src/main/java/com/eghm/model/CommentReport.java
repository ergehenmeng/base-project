package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.eghm.enums.ref.ObjectType;
import com.eghm.enums.ref.ReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论举报记录表
 * </p>
 *
 * @author 二哥很猛
 * @since 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("comment_report")
public class CommentReport extends BaseEntity {

    @ApiModelProperty(value = "举报用户ID")
    private Long memberId;

    @ApiModelProperty(value = "评价ID")
    private Long commentId;

    @ApiModelProperty(value = "评论对象ID")
    private Long objectId;

    @ApiModelProperty(value = "评论对象类型")
    private ObjectType objectType;

    @ApiModelProperty(value = "举报类型")
    private ReportType reportType;

    @ApiModelProperty(value = "举报内容")
    private String content;

}
