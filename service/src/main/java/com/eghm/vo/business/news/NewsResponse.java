package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/7/25
 */

@Data
public class NewsResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "资讯标题")
    private String title;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "标签")
    private String tagName;

    @Schema(description = "图集")
    private String image;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "资讯状态")
    private Boolean state;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
