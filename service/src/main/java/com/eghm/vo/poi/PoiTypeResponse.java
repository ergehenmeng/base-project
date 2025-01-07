package com.eghm.vo.poi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * poi类型表
 * </p>
 *
 * @author 二哥很猛
 * @since 2023-12-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PoiTypeResponse {

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "poi类型名称")
    private String title;

    @Schema(description = "区域编号")
    private String areaCode;

    @Schema(description = "区域名称")
    private String areaTitle;

    @Schema(description = "点位类型icon")
    private String icon;

    @Schema(description = "点位排序 小<->大")
    private Integer sort;

    @Schema(description = "添加时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
