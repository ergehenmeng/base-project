package com.eghm.business.operation.interaction.dto;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 二哥很猛
 * @since 2024/1/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommentQueryDTO extends PagingQuery {

    @Schema(description = "评价对象id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long objectId;

    @Schema(description = "父评论ID")
    private Long pid;
}
