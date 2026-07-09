package com.eghm.vo.business.news;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 资讯配置详情响应
 *
 * @author 二哥很猛
 */
@Data
public class NewsConfigDetailResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "分类标题")
    private String title;

    @Schema(description = "资讯编码")
    private String code;

    @Schema(description = "是否包含标题")
    private Boolean includeTitle;

    @Schema(description = "是否包含标签")
    private Boolean includeTag;

    @Schema(description = "是否包含描述信息")
    private Boolean includeDepict;

    @Schema(description = "是否包含图集")
    private Boolean includeImage;

    @Schema(description = "是否包含详细信息")
    private Boolean includeContent;

    @Schema(description = "是否包含视频")
    private Boolean includeVideo;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
