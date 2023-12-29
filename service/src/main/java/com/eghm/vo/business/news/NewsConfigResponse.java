package com.eghm.vo.business.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */
@Data
public class NewsConfigResponse {

    @ApiModelProperty(value = "分类标题")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    private String code;

    @ApiModelProperty(value = "是否包含标题")
    private Boolean includeTitle;

    @ApiModelProperty(value = "是否包含描述信息")
    private Boolean includeDepict;

    @ApiModelProperty(value = "是否包含图集")
    private Boolean includeImage;

    @ApiModelProperty(value = "是否包含详细信息")
    private Boolean includeContent;

    @ApiModelProperty(value = "是否包含视频")
    private Boolean includeVideo;
}
