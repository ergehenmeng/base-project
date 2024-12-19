package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资讯信息表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("news")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class News extends BaseEntity {

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "资讯编码")
    private String code;

    @ApiModelProperty(value = "一句话描述信息")
    private String depict;

    @ApiModelProperty(value = "标签列表")
    private String tagName;

    @ApiModelProperty(value = "图集")
    private String image;

    @ApiModelProperty(value = "详细信息")
    private String content;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty("点赞数量")
    private Integer likeNum;

    @ApiModelProperty("是否支持评论")
    private Boolean commentSupport;

    @ApiModelProperty("状态 false:不显示 true:显示")
    private Boolean state;

}
