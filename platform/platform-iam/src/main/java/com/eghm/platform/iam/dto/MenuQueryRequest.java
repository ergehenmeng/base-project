package com.eghm.platform.iam.dto;

import com.eghm.foundation.core.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 二哥很猛
 * @since 2024/5/27
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class MenuQueryRequest extends PagingQuery {

    @Schema(description = "父节点Id")
    @NotEmpty(message = "请选择左侧菜单")
    private String pid;
}

