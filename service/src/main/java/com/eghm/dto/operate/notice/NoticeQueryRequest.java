package com.eghm.dto.operate.notice;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2018/11/20 19:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeQueryRequest extends PagingQuery {

    @Schema(description = "公告类型")
    private Integer noticeType;

    @Schema(description = "公告状态 0:未发布 1:已发布")
    private Integer state;
}
