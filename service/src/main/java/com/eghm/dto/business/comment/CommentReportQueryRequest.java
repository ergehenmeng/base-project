package com.eghm.dto.business.comment;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ObjectType;
import com.eghm.enums.ref.ReportType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class CommentReportQueryRequest extends PagingQuery {

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @ApiModelProperty(value = "举报类型")
    private ReportType reportType;
}
