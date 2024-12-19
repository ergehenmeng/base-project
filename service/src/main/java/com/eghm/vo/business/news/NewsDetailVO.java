package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDetailVO {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "一句话描述信息")
    private String depict;

    @ApiModelProperty(value = "图集")
    private String image;

    @ApiModelProperty(value = "标签列表")
    private String tagName;

    @ApiModelProperty(value = "详细信息")
    private String content;

    @ApiModelProperty(value = "视频")
    private String video;

    @ApiModelProperty("点赞数量")
    private Integer praiseNum;

    @ApiModelProperty("是否收藏")
    private Boolean collect;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否已点赞")
    private Boolean hasPraise;

    @ApiModelProperty("是否支持评论")
    private Boolean commentSupport;

}
