package com.eghm.dto.business.news.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
public class NewsConfigAddRequest {

    @Schema(description = "分类标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "分类标题不能为空")
    @Size(max = 10, message = "分类标题最大10字符")
    private String title;

    @Schema(description = "资讯编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "资讯编码不能为空")
    @Size(max = 20, message = "资讯编码最大20字符")
    private String code;

    @Schema(description = "是否包含标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含标题不能为空")
    private Boolean includeTitle;

    @Schema(description = "是否包含标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含标签不能为空")
    private Boolean includeTag;

    @Schema(description = "是否包含描述信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含描述信息不能为空")
    private Boolean includeDepict;

    @Schema(description = "是否包含图集", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含图集不能为空")
    private Boolean includeImage;

    @Schema(description = "是否包含详细信息", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含详细信息不能为空")
    private Boolean includeContent;

    @Schema(description = "是否包含视频", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否包含视频不能为空")
    private Boolean includeVideo;
}
