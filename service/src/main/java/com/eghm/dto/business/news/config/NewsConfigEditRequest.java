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

    @ApiModelProperty(value = "id主键")
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiModelProperty(value = "分类标题")
    @NotBlank(message = "分类标题不能为空")
    @Size(max = 10, message = "分类标题最大10字符")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    @NotBlank(message = "资讯编码不能为空")
    @Size(max = 20, message = "资讯编码最大20字符")
    private String code;

    @ApiModelProperty(value = "是否包含标题")
    @NotNull(message = "是否包含标题不能为空")
    private Boolean includeTitle;

    @ApiModelProperty(value = "是否包含描述信息")
    private Boolean includeDepict;

    @ApiModelProperty(value = "是否包含图集")
    private Boolean includeImage;

    @ApiModelProperty(value = "是否包含详细信息")
    @NotNull(message = "是否包含详细信息不能为空")
    private Boolean includeContent;

    @ApiModelProperty(value = "是否包含视频")
    private Boolean includeVideo;
}
