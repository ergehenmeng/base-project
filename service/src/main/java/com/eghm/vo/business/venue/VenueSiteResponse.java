package com.eghm.vo.business.venue;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 二哥很猛
 * @since 2024/2/2
 */

@Data
public class VenueSiteResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "场地封面图片")
    private String coverUrl;

    @Schema(description = "场地名称")
    private String title;

    @Schema(description = "所属场馆id")
    private String venueId;

    @Schema(description = "所属场馆")
    private String venueName;

    @Schema(description = "状态 0:待上架 1:已上架 2:强制下架")
    private Integer state;

    @Schema(description = "排序 小<->大")
    private Integer sort;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
