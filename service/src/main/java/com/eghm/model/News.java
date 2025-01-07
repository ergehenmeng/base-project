package com.eghm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "资讯标题")
    private String title;

    @Schema(description = "资讯编码")
    private String code;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "图集")
    private String image;

    @Schema(description = "标签列表")
    private String tagName;

    @Schema(description = "详细信息")
    private String content;

    @Schema(description = "视频")
    private String video;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态 0:未发布 1:已发布")
    private Boolean state;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "是否支持评论")
    private Boolean commentSupport;

}
