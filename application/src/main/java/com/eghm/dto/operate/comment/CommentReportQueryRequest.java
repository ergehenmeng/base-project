package com.eghm.dto.operate.comment;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ObjectType;
import com.eghm.enums.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentReportQueryRequest extends PagingQuery {

    @Schema(description = "评论id")
    private Long commentId;

    @Schema(description = "对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "举报类型")
    private ReportType reportType;
}
