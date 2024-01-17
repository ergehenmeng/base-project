package com.eghm.dto.business.comment;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentQueryDTO extends PagingQuery {

    @ApiModelProperty("评价对象id")
    @NotNull(message = "id不能为空")
    private Long objectId;
}
