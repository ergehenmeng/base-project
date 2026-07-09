package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 管理端资讯详情响应
 *
 * @author 二哥很猛
 */
@Data
public class NewsDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "资讯标题")
    private String title;

    @Schema(description = "资讯编码")
    private String code;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "标签列表")
    private String tagName;

    @Schema(description = "图集")
    private String image;

    @Schema(description = "详细信息")
    private String content;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;

    @Schema(description = "状态 true:显示 false:隐藏")
    private Boolean state;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
