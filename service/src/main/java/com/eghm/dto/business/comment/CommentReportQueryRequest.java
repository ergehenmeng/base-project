package com.eghm.dto.business.comment;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ObjectType;
import com.eghm.enums.ref.ReportType;
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

    @Schema(description = "举报原因 (1:淫秽色情 2:营销广告 3:违法信息 4:网络暴力 5:虚假谣言 6:养老诈骗 7:其他)")
    private ReportType reportType;
}
