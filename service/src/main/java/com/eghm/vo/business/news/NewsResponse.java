package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class NewsResponse {

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty(value = "资讯标题")
    private String title;

    @ApiModelProperty(value = "一句话描述信息")
    private String depict;

    @ApiModelProperty(value = "标签")
    private String tagName;

    @ApiModelProperty(value = "图集")
    private String image;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty("资讯状态")
    private Boolean state;

    @ApiModelProperty("点赞数量")
    private Integer praiseNum;

    @ApiModelProperty("是否支持评论")
    private Boolean commentSupport;

    @ApiModelProperty("添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
