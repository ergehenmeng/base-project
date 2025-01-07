package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2023/12/29
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDetailVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "资讯标题")
    private String title;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "图集")
    private String image;

    @Schema(description = "详细信息")
    private String content;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "标签列表")
    private String tagName;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "是否收藏")
    private Boolean collect;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "是否已点赞")
    private Boolean hasPraise;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;

}
