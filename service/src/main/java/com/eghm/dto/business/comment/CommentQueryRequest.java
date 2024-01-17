package com.eghm.dto.business.comment;

import com.eghm.dto.ext.PagingQuery;
import com.eghm.enums.ref.ObjectType;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("对象类型")
    private ObjectType objectType;

    @ApiModelProperty(value = "对象id", hidden = true)
    private List<Long> objectIds;

    @ApiModelProperty("状态 false:已屏蔽 true:正常")
    private Boolean state;

    @ApiModelProperty("置顶状态 0:未置顶 1:置顶")
    private Integer topState;
}
