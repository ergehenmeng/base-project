package com.eghm.dto.business.comment;

import com.eghm.annotation.Assign;
import com.eghm.enums.ref.ReportType;
import com.eghm.validation.annotation.WordChecker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
public class CommentReportDTO {

    @Schema(description = "举报类型")
    @NotNull(message = "请选择要举报的类型")
    private ReportType reportType;

    @Schema(description = "举报内容")
    @Size(max = 100, message = "举报内容最大100字符")
    @WordChecker(message = "举报内容包含敏感词汇")
    private String content;

    @Schema(description = "评论id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "请选择要举报的评论")
    private Long commentId;

    @Schema(description = "用户id", hidden = true)
    @Assign
    private Long memberId;
}
