package com.eghm.dto.business.news;

import com.eghm.dto.ext.PagingQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.NotBlank;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class NewsQueryRequest extends PagingQuery {

    @Schema(description = "资讯编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "资讯编码不能为空")
    private String code;

    @Schema(description = "状态 true:显示 false:隐藏")
    private Boolean state;
}
