package com.eghm.dto.business.news.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
public class NewsConfigEditRequest {

    @ApiModelProperty(value = "id主键", required = true)
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "分类标题", required = true)
    @NotBlank(message = "分类标题不能为空")
    @Size(max = 10, message = "分类标题最大10字符")
    private String title;

    @ApiModelProperty(value = "是否包含标题", required = true)
    @NotNull(message = "是否包含标题不能为空")
    private Boolean includeTitle;

    @ApiModelProperty(value = "是否包含标签", required = true)
    @NotNull(message = "是否包含标签不能为空")
    private Boolean includeTag;

    @ApiModelProperty(value = "是否包含描述信息", required = true)
    @NotNull(message = "是否包含描述信息不能为空")
    private Boolean includeDepict;

    @ApiModelProperty(value = "是否包含图集", required = true)
    @NotNull(message = "是否包含图集不能为空")
    private Boolean includeImage;

    @ApiModelProperty(value = "是否包含详细信息", required = true)
    @NotNull(message = "是否包含详细信息不能为空")
    private Boolean includeContent;

    @ApiModelProperty(value = "是否包含视频", required = true)
    @NotNull(message = "是否包含视频不能为空")
    private Boolean includeVideo;
}
