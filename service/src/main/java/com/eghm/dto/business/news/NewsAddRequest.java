package com.eghm.dto.business.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
public class NewsAddRequest {

    @ApiModelProperty(value = "资讯标题")
    @NotBlank(message = "标题不能为空")
    @Size(max = 20, message = "标题长度不能超过20")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    @NotBlank(message = "编码不能为空")
    @Size(max = 20, message = "编码长度不能超过20")
    private String code;

    @ApiModelProperty(value = "一句话描述信息")
    private String depict;

    @ApiModelProperty(value = "图集")
    private String image;

    @ApiModelProperty(value = "详细信息")
    @NotBlank(message = "详细信息不能为空")
    private String content;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
