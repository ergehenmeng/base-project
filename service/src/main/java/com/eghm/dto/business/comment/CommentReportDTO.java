package com.eghm.dto.business.comment;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.ReportType;
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
public class CommentReportDTO {

    @ApiModelProperty(value = "举报类型")
    @NotNull(message = "请选择要举报的类型")
    private ReportType reportType;

    @ApiModelProperty(value = "举报内容")
    @Size(max = 100, message = "举报内容最大100字符")
    @WordChecker(message = "举报内容包含敏感词汇")
    private String content;

    @ApiModelProperty(value = "评论id", required = true)
    @NotNull(message = "请选择要举报的评论")
    private Long commentId;

    @ApiModelProperty(value = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
