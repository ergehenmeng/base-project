package com.eghm.dto.business.comment;

import com.eghm.annotation.Assign;
import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ObjectType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentQueryRequest extends PagingQuery {

    @Schema(description = "对象类型 (1:资讯 2:活动)")
    private ObjectType objectType;

    @Schema(description = "状态 false:已屏蔽 true:正常")
    private Boolean state;

    @Schema(description = "置顶状态 0:未置顶 1:置顶")
    private Integer topState;

    @Schema(description = "对象id", hidden = true)
    @Assign
    private List<Long> objectIds;
}
