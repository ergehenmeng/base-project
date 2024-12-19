package com.eghm.dto.business.news.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    private Boolean includeTag;

    @ApiModelProperty(value = "是否包含描述信息", required = true)
    private Boolean includeDepict;

    @ApiModelProperty(value = "是否包含图集", required = true)
    private Boolean includeImage;

    @ApiModelProperty(value = "是否包含详细信息", required = true)
    @NotNull(message = "是否包含详细信息不能为空")
    private Boolean includeContent;

    @ApiModelProperty(value = "是否包含视频", required = true)
    private Boolean includeVideo;
}
