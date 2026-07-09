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
public class NewsVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "资讯标题")
    private String title;

    @Schema(description = "标签列表")
    private String tagName;

    @Schema(description = "一句话描述信息")
    private String depict;

    @Schema(description = "图集")
    private String image;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Schema(description = "点赞数量")
    private Integer praiseNum;

    @Schema(description = "是否已点赞")
    private Boolean hasPraise;

    @Schema(description = "状态 false:未上架 true:已上架")
    private Boolean state;

    @Schema(description = "是否已删除 false:未删除 true:已删除")
    private Boolean deleted;
}
